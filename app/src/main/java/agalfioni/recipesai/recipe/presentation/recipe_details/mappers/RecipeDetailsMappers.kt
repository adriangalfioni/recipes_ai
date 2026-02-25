package agalfioni.recipesai.recipe.presentation.recipe_details.mappers

import agalfioni.recipesai.recipe.domain.models.Recipe
import agalfioni.recipesai.recipe.presentation.recipe_details.models.RecipeDetailsUi
import agalfioni.recipesai.recipe.presentation.recipe_list.mappers.toIngredientList
import agalfioni.recipesai.recipe.presentation.recipe_list.mappers.toInstructionList
import kotlin.math.roundToInt
import kotlin.time.DurationUnit
import kotlin.time.toDuration


fun Recipe.toRecipeDetailsUi(): RecipeDetailsUi {
    return RecipeDetailsUi(
        id = id,
        title = title,
        minutesTime = formatMinutesToHourMinutes(minutesTime),
        category = "",
        totalCalories = formatTotalCalories(nutrition.calories),
        aiScore = ingredientCoverage.roundToInt(),
        chefInsight = null,
        ingredients = ingredients.toIngredientList(),
        instructions = instructions.toInstructionList()
    )
}

private fun formatTotalCalories(calories: Double): String {
    return "${calories.roundToInt()} kcal"
}

private fun formatMinutesToHourMinutes(totalMinutes: Int): String {

    val duration = totalMinutes.toDuration(DurationUnit.MINUTES)

    return duration.toComponents { hours, minutes, _, _ ->
        "%d:%02d".format(hours, minutes)
    }
}

private fun coverageToString(coverage: Double): String {
    return "${coverage.roundToInt()}%"
}
