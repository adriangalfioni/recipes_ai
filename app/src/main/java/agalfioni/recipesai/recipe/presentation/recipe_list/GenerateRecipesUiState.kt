package agalfioni.recipesai.recipe.presentation.recipe_list

import agalfioni.recipesai.core.presentation.utils.UiOneTimeEvent
import agalfioni.recipesai.core.presentation.utils.UiText
import agalfioni.recipesai.recipe.domain.models.Recipe
import agalfioni.recipesai.recipe.presentation.recipe_list.models.RecipeUi

data class GenerateRecipesUiState(
    val isLoading: Boolean = false,
    val recipes: List<RecipeUi> = emptyList(),
    val error: UiText? = null,
    val navigateToRecipeEvent: UiOneTimeEvent<Recipe>? = null
)
