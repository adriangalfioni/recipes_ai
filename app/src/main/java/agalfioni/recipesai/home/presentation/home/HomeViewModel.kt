package agalfioni.recipesai.home.presentation.home



import agalfioni.recipesai.core.presentation.extensions.ingredientsSuggestionsFlow
import agalfioni.recipesai.core.presentation.utils.removeStressAccents
import agalfioni.recipesai.home.domain.interfaces.IngredientsRepository
import agalfioni.recipesai.recipe_list.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe_list.presentation.mappers.toRecipeUiList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.Locale

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val ingredientsRepository: IngredientsRepository,
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    // 1. Raw inputs (StateHolders)
    private var _queryFlow = MutableStateFlow("")
    private val _addedIngredientsFlow = MutableStateFlow<List<String>>(emptyList())

    private val _localIngredientsFlow = flow {
        ingredientsRepository.getLocalIngredients()
            .onSuccess { ingredientsList ->
                val allLocalIngredients = ingredientsList.map {
                    if (Locale.getDefault().language == "es") {
                        it.es.removeStressAccents()
                    } else {
                        it.en
                    }
                }
                emit(allLocalIngredients)
            }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _suggestionsFlow = _queryFlow.ingredientsSuggestionsFlow(_localIngredientsFlow)

    private val _recentRecipesFlow = recipeRepository.getAllRecipes()
        .map { it.toRecipeUiList() }
        .catch { _ -> emit(emptyList()) }

    val uiState: StateFlow<HomeUiState> = combine(
        _queryFlow,
        _addedIngredientsFlow,
        _localIngredientsFlow,
        _suggestionsFlow,
        _recentRecipesFlow
    ) { query, addedIngredients, allLocalIngredients, suggestions, recentRecipes ->
        HomeUiState(
            addedIngredients = addedIngredients,
            allLocalIngredients = allLocalIngredients,
            query = query,
            suggestions = suggestions,
            showSuggestions = suggestions.isNotEmpty() && query.isNotBlank(),
            recentRecipes = recentRecipes
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
    )

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnIngredientRemoved -> onIngredientRemoved(event.item)
            is HomeEvent.OnQueryChanged -> onQueryChanged(event.query)
            is HomeEvent.OnSuggestionSelected -> onSuggestionSelected(event.suggestedItem)
            HomeEvent.OnClearAll -> _addedIngredientsFlow.update { emptyList() }
            HomeEvent.OnGenerateRecipes -> {}
        }
    }

    private fun onIngredientRemoved(itemToRemove: String) {
        _addedIngredientsFlow.update { it - itemToRemove }
    }

    fun onQueryChanged(query: String) {
        _queryFlow.value = query
    }

    fun onSuggestionSelected(item: String) {
        _addedIngredientsFlow.update {
            it.toSet().plus(item).sorted()
        }
        _queryFlow.value = ""
    }

}