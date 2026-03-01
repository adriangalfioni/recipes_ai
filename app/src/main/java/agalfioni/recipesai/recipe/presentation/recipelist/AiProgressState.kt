package agalfioni.recipesai.recipe.presentation.recipelist

import agalfioni.recipesai.recipe.presentation.recipelist.models.AiStep

data class AiProgressState(
    val progress: Int = 0,
    val stepIndex: Int = 0,
    val isAccelerating: Boolean = false,
    val hasFinished: Boolean = false,
    val steps: List<AiStep> = emptyList(),
)
