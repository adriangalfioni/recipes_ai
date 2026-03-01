package agalfioni.recipesai.recipe.presentation.recipelist.models

import agalfioni.recipesai.core.presentation.utils.UiText

data class AiStep(
    val message: UiText,
    val targetProgressPercentage: Int,
)
