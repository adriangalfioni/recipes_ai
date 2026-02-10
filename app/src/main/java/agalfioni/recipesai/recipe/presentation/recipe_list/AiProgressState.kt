package agalfioni.recipesai.recipe.presentation.recipe_list

import agalfioni.recipesai.recipe.presentation.recipe_list.models.AiStep

data class AiProgressState(
    val progress: Int = 0,
    val stepIndex: Int = 0,
    val isAccelerating: Boolean = false,
    val hasFinished: Boolean = false,
    val steps: List<AiStep> = emptyList()
)