package agalfioni.recipesai.core.recipes.presentation.mappers

import agalfioni.recipesai.core.recipes.domain.models.Recipe
import agalfioni.recipesai.core.recipes.domain.models.RecipeIngredient
import agalfioni.recipesai.core.recipes.domain.models.RecipeInstruction
import agalfioni.recipesai.core.recipes.domain.models.toDisplayString
import agalfioni.recipesai.recipe.recipe_details.presentation.models.Ingredient
import agalfioni.recipesai.recipe.recipe_details.presentation.models.Instruction
import agalfioni.recipesai.recipe.recipe_list.presentation.models.RecipeUi
import kotlin.collections.map
import kotlin.math.roundToInt
import kotlin.time.DurationUnit
import kotlin.time.toDuration


fun List<Recipe>.toRecipeUiList(): List<RecipeUi> {
    return map { it.toRecipeUi() }
}

fun List<RecipeIngredient>.toIngredientList(): List<Ingredient> {
    return map { Ingredient(it.name, "${it.quantity} ${it.unit}") }
}

fun List<RecipeInstruction>.toInstructionList(): List<Instruction> {
    return mapIndexed { index, it ->
        Instruction(
            step = index + 1,
            title = it.title,
            description = it.description
        )
    }
}

private fun Recipe.toRecipeUi(): RecipeUi {
    return RecipeUi(
        id = id,
        title = title,
        difficulty = difficulty.toDisplayString(),
        minutesTime = formatMinutesToHourMinutes(minutesTime),
        ingredientCoveragePercentage = coverageToString(ingredientCoverage),
        isMatchHigh = ingredientCoverage >= 80,
        totalCalories = formatTotalCalories(nutrition.calories)
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
