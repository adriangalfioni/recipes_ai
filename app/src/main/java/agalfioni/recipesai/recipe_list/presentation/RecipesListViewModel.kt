package agalfioni.recipesai.recipe_list.presentation

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.presentation.utils.asUiText
import agalfioni.recipesai.recipe_list.domain.GenerateRecipesRepository
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import agalfioni.recipesai.recipe_list.presentation.mappers.toRecipeUiList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withTimeout

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class RecipesListViewModel(
    private val ingredients: List<String>,
    private val generateRecipesRepository: GenerateRecipesRepository
) : ViewModel() {

    // 1. The Trigger: An event stream that starts with a 'Unit' to load immediately
    private val retryTrigger = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    val uiState: StateFlow<GenerateRecipesUiState> = retryTrigger.flatMapLatest {
        flow {
            emit(GenerateRecipesUiState(isLoading = true))

            val result = withTimeout(6_000L) {
                generateRecipesRepository.generateRecipes(
                    ingredients = ingredients,
                    recipesQty = NUMBER_OF_RECIPES_TO_GENERATE
                )
            }

            when (result) {
                is AppResult.Success -> {
                    receivedRecipes.clear()
                    receivedRecipes.addAll(result.data)

                    emit(
                        GenerateRecipesUiState(
                            recipes = result.data.toRecipeUiList()
                        )
                    )
                }
                is AppResult.Error -> {
                    emit(
                        GenerateRecipesUiState(
                            error = result.error.asUiText()
                        )
                    )
                }
            }
        }
        .catch { e ->
            val errorType = if (e is TimeoutCancellationException) {
                DataError.DEADLINE_EXCEEDED
            } else {
                DataError.UNKNOWN
            }
            emit(GenerateRecipesUiState(error = errorType.asUiText()))
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GenerateRecipesUiState(isLoading = true)
    )

    private val receivedRecipes: MutableList<Recipe> = mutableListOf()

    companion object {
        private const val NUMBER_OF_RECIPES_TO_GENERATE = 10
    }

    fun onEvent(event: RecipeListEvent) {
        when (event) {
            RecipeListEvent.OnRetry -> retryTrigger.tryEmit(Unit)
        }
    }

}
