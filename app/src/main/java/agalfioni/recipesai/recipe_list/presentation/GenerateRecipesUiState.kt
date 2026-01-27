package agalfioni.recipesai.recipe_list.presentation

import agalfioni.recipesai.core.presentation.utils.UiText
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import agalfioni.recipesai.recipe_list.presentation.models.RecipeUi

data class GenerateRecipesUiState(
    val isLoading: Boolean = false,
    val recipes: List<RecipeUi> = emptyList(),
    val error: UiText? = null,
    val navigateToRecipe: Recipe? = null
)
