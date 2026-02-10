package agalfioni.recipesai.recipe.presentation.recipe_details

import agalfioni.recipesai.core.presentation.utils.UiText

data class RecipeDetailsState(
    val isLoading: Boolean = false,
    val recipeDetailsUi: agalfioni.recipesai.recipe.presentation.recipe_details.models.RecipeDetailsUi? = null,
    val error: UiText? = null
)
