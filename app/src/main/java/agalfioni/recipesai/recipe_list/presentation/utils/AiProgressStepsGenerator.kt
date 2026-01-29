package agalfioni.recipesai.recipe_list.presentation.utils

import agalfioni.recipesai.recipe_list.presentation.models.AiStep

object AiProgressStepsGenerator {

    fun generate(): List<AiStep> = listOf(
        AiStep("Analyzing ingredients…", 5),
        AiStep("Normalizing ingredient names…", 15),
        AiStep("Checking ingredient compatibility…", 25),
        AiStep("Scoring ingredient coverage…", 40),
        AiStep("Searching recipe combinations…", 50),
        AiStep("Selecting best recipe matches…", 70),
        AiStep("Estimating nutritional values…", 85),
        AiStep("Finalizing recipes…", 100)
    )
}