package agalfioni.recipesai.recipe_list.presentation

import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.onFailure
import agalfioni.recipesai.core.domain.models.onSuccess
import agalfioni.recipesai.core.presentation.utils.asUiText
import agalfioni.recipesai.recipe_list.domain.usecase.GenerateRecipesUseCase
import agalfioni.recipesai.recipe_list.presentation.mappers.toRecipeUiList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class RecipesListViewModel(
    private val ingredients: List<String>,
    private val generateRecipesUseCase: GenerateRecipesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(GenerateRecipesUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        generateRecipes()
    }

    private fun generateRecipes() {
        viewModelScope.launch {
            withTimeout(IA_GENERATION_TIMEOUT_MILLIS) {
                try {
                    generateRecipesUseCase(
                        ingredients = ingredients,
                        recipesQty = NUMBER_OF_RECIPES_TO_GENERATE
                    ).onSuccess { result ->
                        _uiState.update {
                            it.copy(recipes = result.toRecipeUiList())
                        }
                    }.onFailure { dataError ->
                        _uiState.update {
                            it.copy(error = dataError.asUiText())
                        }
                    }
                } catch (e: TimeoutCancellationException) {
                    _uiState.update {
                        it.copy(error = DataError.DEADLINE_EXCEEDED.asUiText())
                    }
                }
            }
        }
    }

    fun onEvent(event: RecipeListEvent) {
        when (event) {
            RecipeListEvent.OnRetry -> {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        error = null
                    )
                }
                generateRecipes()
            }
        }
    }

    companion object {
        private const val NUMBER_OF_RECIPES_TO_GENERATE = 10
        private const val IA_GENERATION_TIMEOUT_MILLIS = 60_000L
    }
}
