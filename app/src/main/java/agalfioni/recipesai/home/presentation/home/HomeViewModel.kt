package agalfioni.recipesai.home.presentation.home

import agalfioni.recipesai.core.presentation.extensions.ingredientsSuggestionsFlow
import agalfioni.recipesai.core.presentation.utils.removeStressAccents
import agalfioni.recipesai.ingredientsdetector.domain.interfaces.IngredientsRepository
import agalfioni.recipesai.recipe.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe.presentation.recipelist.mappers.toRecipeUi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.Locale

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val ingredientsRepository: IngredientsRepository,
    recipeRepository: RecipeRepository,
) : ViewModel() {
    /*
     * ---------------------------------------------------------------------------------------
     * UI STATE ARCHITECTURE (MVVM + MVI Approach) - Learning purpose :)
     * ---------------------------------------------------------------------------------------
     * Using a 'Single Source of Truth' pattern by combining multiple private flows:
     * 1. STATE STREAMS: We observe private flows for the query, ingredients local data and suggestions.
     * 2. COMBINE: The 'combine' operator merges these streams into a single HomeUiState object.
     * 3. IMMUTABILITY: Every change in a private flow triggers a new emission of the UI state,
     * ensuring the View remains a "passive observer" of the data.
     * 4. EFFICIENCY: 'stateIn' with 'WhileSubscribed' ensures that if the user leaves the screen,
     * we stop calculating suggestions and save resources after a 5-second delay.
     * ---------------------------------------------------------------------------------------
     */

    private var queryFlow = MutableStateFlow("")
    private val addedIngredientsFlow = MutableStateFlow<List<String>>(emptyList())

    private val localIngredientsFlow =
        flow {
            ingredientsRepository
                .getLocalIngredients()
                .onSuccess { ingredientsList ->
                    val allLocalIngredients =
                        ingredientsList.map {
                            if (Locale.getDefault().language == "es") {
                                it.es.removeStressAccents()
                            } else {
                                it.en
                            }
                        }
                    emit(allLocalIngredients)
                }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val suggestionsFlow = queryFlow.ingredientsSuggestionsFlow(localIngredientsFlow)

    val recentRecipes =
        recipeRepository
            .getRecipes()
            .map { pagingData ->
                pagingData.map { recipe ->
                    recipe.toRecipeUi()
                }
            }.cachedIn(viewModelScope)

    val uiState: StateFlow<HomeUiState> =
        combine(
            queryFlow,
            addedIngredientsFlow,
            localIngredientsFlow,
            suggestionsFlow,
        ) { query, addedIngredients, allLocalIngredients, suggestions ->
            HomeUiState(
                addedIngredients = addedIngredients,
                allLocalIngredients = allLocalIngredients,
                query = query,
                suggestions = suggestions,
                showSuggestions = suggestions.isNotEmpty() && query.isNotBlank(),
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(),
        )

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnIngredientRemoved -> onIngredientRemoved(event.item)
            is HomeEvent.OnQueryChanged -> onQueryChanged(event.query)
            is HomeEvent.OnSuggestionSelected -> onSuggestionSelected(event.suggestedItem)
            HomeEvent.OnClearAll -> addedIngredientsFlow.update { emptyList() }
            HomeEvent.OnGenerateRecipes -> {}
        }
    }

    private fun onIngredientRemoved(itemToRemove: String) {
        addedIngredientsFlow.update { it - itemToRemove }
    }

    fun onQueryChanged(query: String) {
        queryFlow.value = query
    }

    fun onSuggestionSelected(item: String) {
        addedIngredientsFlow.update {
            it.toSet().plus(item).sorted()
        }
        queryFlow.value = ""
    }
}
