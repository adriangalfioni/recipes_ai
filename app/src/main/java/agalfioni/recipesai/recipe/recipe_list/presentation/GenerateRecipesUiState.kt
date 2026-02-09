package agalfioni.recipesai.recipe.recipe_list.presentation

import agalfioni.recipesai.core.presentation.utils.UiOneTimeEvent
import agalfioni.recipesai.core.presentation.utils.UiText
import agalfioni.recipesai.core.recipes.domain.models.Recipe
import agalfioni.recipesai.recipe.recipe_list.presentation.models.RecipeUi

data class GenerateRecipesUiState(
    val isLoading: Boolean = false,
    val recipes: List<RecipeUi> = emptyList(),
    val error: UiText? = null,
    val navigateToRecipeEvent: UiOneTimeEvent<Recipe>? = null
)
