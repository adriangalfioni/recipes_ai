package agalfioni.recipesai.recipe.presentation.recipedetails.mappers

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.utils.UiText
import agalfioni.recipesai.recipe.domain.models.RecipeDetailsError

fun RecipeDetailsError.asUiText(): UiText =
    when (this) {
        RecipeDetailsError.RECIPE_NOT_FOUND -> UiText.StringResource(R.string.recipe_not_found)
    }
