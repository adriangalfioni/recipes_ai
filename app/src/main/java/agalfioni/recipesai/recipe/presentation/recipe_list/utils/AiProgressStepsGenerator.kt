package agalfioni.recipesai.recipe.presentation.recipe_list.utils

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.utils.UiText
import agalfioni.recipesai.recipe.presentation.recipe_list.models.AiStep

object AiProgressStepsGenerator {

    fun generate(): List<AiStep> = listOf(
        AiStep(UiText.StringResource(R.string.analyzing_ingredients), 10),
        AiStep(UiText.StringResource(R.string.normalizing_ingredient_names), 25),
        AiStep(UiText.StringResource(R.string.checking_ingredient_compatibility), 35),
        AiStep(UiText.StringResource(R.string.scoring_ingredient_coverage), 40),
        AiStep(UiText.StringResource(R.string.searching_recipe_combinations), 50),
        AiStep(UiText.StringResource(R.string.selecting_best_recipe_matches), 70),
        AiStep(UiText.StringResource(R.string.estimating_nutritional_values), 85),
        AiStep(UiText.StringResource(R.string.finalizing_recipes), 100)
    )
}