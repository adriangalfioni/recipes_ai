package agalfioni.recipesai.recipe.presentation.recipedetails

import agalfioni.recipesai.core.presentation.utils.UiText

data class RecipeDetailsState(
    val isLoading: Boolean = false,
    val recipeDetailsUi: agalfioni.recipesai.recipe.presentation.recipedetails.models.RecipeDetailsUi? = null,
    val error: UiText? = null,
)
