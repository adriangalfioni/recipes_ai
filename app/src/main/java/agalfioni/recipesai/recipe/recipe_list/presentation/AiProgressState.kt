package agalfioni.recipesai.recipe.recipe_list.presentation

import agalfioni.recipesai.recipe.recipe_list.presentation.models.AiStep


data class AiProgressState(
    val progress: Int = 0,
    val stepIndex: Int = 0,
    val isAccelerating: Boolean = false,
    val hasFinished: Boolean = false,
    val steps: List<AiStep> = emptyList()
)