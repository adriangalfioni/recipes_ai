package agalfioni.recipesai.recipe.recipe_details.presentation

import agalfioni.recipesai.core.presentation.utils.UiText
import agalfioni.recipesai.recipe.recipe_details.presentation.models.RecipeDetailsUi

data class RecipeDetailsState(
    val isLoading: Boolean = false,
    val recipeDetailsUi: RecipeDetailsUi? = null,
    val error: UiText? = null
)
