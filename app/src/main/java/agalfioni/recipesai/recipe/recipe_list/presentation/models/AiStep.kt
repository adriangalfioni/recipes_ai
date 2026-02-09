package agalfioni.recipesai.recipe.recipe_list.presentation.models

import agalfioni.recipesai.core.presentation.utils.UiText

data class AiStep(
    val message: UiText,
    val targetProgressPercentage: Int
)
