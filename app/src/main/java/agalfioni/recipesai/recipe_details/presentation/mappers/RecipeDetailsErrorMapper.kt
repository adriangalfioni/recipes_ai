package agalfioni.recipesai.recipe_details.presentation.mappers

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.utils.UiText
import agalfioni.recipesai.recipe_details.domain.RecipeDetailsError

fun RecipeDetailsError.asUiText(): UiText {
    return when (this) {
        RecipeDetailsError.RECIPE_NOT_FOUND -> UiText.StringResource(R.string.recipe_not_found)
    }
}