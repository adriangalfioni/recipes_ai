package agalfioni.recipesai.recipe_list.presentation

import agalfioni.recipesai.core.domain.models.onFailure
import agalfioni.recipesai.core.domain.models.onSuccess
import agalfioni.recipesai.core.presentation.utils.UiOneTimeEvent
import agalfioni.recipesai.core.presentation.utils.asUiText
import agalfioni.recipesai.recipe_list.domain.GenerateRecipesRepository
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import agalfioni.recipesai.recipe_list.presentation.mappers.toRecipeUiList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipesListViewModel(
    private val ingredients: List<String>,
    private val generateRecipesRepository: GenerateRecipesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GenerateRecipesUiState())
    val uiState: StateFlow<GenerateRecipesUiState> = _uiState.asStateFlow()

    private val receivedRecipes: MutableList<Recipe> = mutableListOf()

    companion object {
        private const val NUMBER_OF_RECIPES_TO_GENERATE = 10
    }

    init {
        generateRecipes(
            ingredients = ingredients,
            recipesQty = NUMBER_OF_RECIPES_TO_GENERATE
        )
    }

    fun onEvent(event: RecipeListEvent) {
        when (event) {
            is RecipeListEvent.OnRecipeClicked -> {
                receivedRecipes.firstOrNull { it.title == event.recipeUi.title }?.let { recipe ->
                    _uiState.update {
                        it.copy(
                            navigateToRecipeEvent = UiOneTimeEvent(
                                payload = recipe,
                                onEventConsumed = { _uiState.update { it.copy(navigateToRecipeEvent = null) } }
                            )
                        )
                    }
                }
            }
        }
    }

    fun generateRecipes(
        ingredients: List<String>,
        recipesQty: Int = 10
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    recipes = emptyList()
                )
            }

            generateRecipesRepository.generateRecipes(
                recipesQty = recipesQty,
                ingredients = ingredients
            ).onSuccess { recipes ->

                receivedRecipes.addAll(recipes)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        recipes = recipes.toRecipeUiList()
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = error.asUiText()
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
