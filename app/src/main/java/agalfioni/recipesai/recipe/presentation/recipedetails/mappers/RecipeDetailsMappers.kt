package agalfioni.recipesai.recipe.presentation.recipedetails.mappers

import agalfioni.recipesai.recipe.domain.models.Recipe
import agalfioni.recipesai.recipe.presentation.recipedetails.models.RecipeDetailsUi
import agalfioni.recipesai.recipe.presentation.recipelist.mappers.toIngredientList
import agalfioni.recipesai.recipe.presentation.recipelist.mappers.toInstructionList
import kotlin.math.roundToInt
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun Recipe.toRecipeDetailsUi(): RecipeDetailsUi =
    RecipeDetailsUi(
        id = id,
        title = title,
        minutesTime = formatMinutesToHourMinutes(minutesTime),
        category = "",
        totalCalories = formatTotalCalories(nutrition.calories),
        aiScore = ingredientCoverage.roundToInt(),
        chefInsight = null,
        ingredients = ingredients.toIngredientList(),
        instructions = instructions.toInstructionList(),
    )

private fun formatTotalCalories(calories: Double): String = "${calories.roundToInt()} kcal"

private fun formatMinutesToHourMinutes(totalMinutes: Int): String {
    val duration = totalMinutes.toDuration(DurationUnit.MINUTES)

    return duration.toComponents { hours, minutes, _, _ ->
        "%d:%02d".format(hours, minutes)
    }
}

private fun coverageToString(coverage: Double): String = "${coverage.roundToInt()}%"
