package agalfioni.recipesai.recipe.presentation.recipelist.mappers

import agalfioni.recipesai.recipe.domain.models.Recipe
import agalfioni.recipesai.recipe.domain.models.RecipeIngredient
import agalfioni.recipesai.recipe.domain.models.RecipeInstruction
import agalfioni.recipesai.recipe.domain.models.toDisplayString
import agalfioni.recipesai.recipe.presentation.recipedetails.models.Ingredient
import agalfioni.recipesai.recipe.presentation.recipedetails.models.Instruction
import agalfioni.recipesai.recipe.presentation.recipelist.models.RecipeUi
import kotlin.collections.map
import kotlin.math.roundToInt
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun List<Recipe>.toRecipeUiList(): List<RecipeUi> = map { it.toRecipeUi() }

fun List<RecipeIngredient>.toIngredientList(): List<Ingredient> = map { Ingredient(it.name, "${it.quantity} ${it.unit}") }

fun List<RecipeInstruction>.toInstructionList(): List<Instruction> =
    mapIndexed { index, it ->
        Instruction(
            step = index + 1,
            title = it.title,
            description = it.description,
        )
    }

fun Recipe.toRecipeUi(): RecipeUi =
    RecipeUi(
        id = id,
        title = title,
        difficulty = difficulty.toDisplayString(),
        minutesTime = formatMinutesToHourMinutes(minutesTime),
        ingredientCoveragePercentage = coverageToString(ingredientCoverage),
        isMatchHigh = ingredientCoverage >= 0.8,
        totalCalories = formatTotalCalories(nutrition.calories),
    )

private fun formatTotalCalories(calories: Double): String = "${calories.roundToInt()} kcal"

private fun formatMinutesToHourMinutes(totalMinutes: Int): String {
    val duration = totalMinutes.toDuration(DurationUnit.MINUTES)

    return duration.toComponents { hours, minutes, _, _ ->
        "%d:%02d".format(hours, minutes)
    }
}

private fun coverageToString(coverage: Double): String = "${coverage.roundToInt()}%"
