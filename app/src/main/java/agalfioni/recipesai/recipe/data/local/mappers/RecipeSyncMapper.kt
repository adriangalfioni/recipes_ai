package agalfioni.recipesai.recipe.data.local.mappers

import agalfioni.recipesai.recipe.data.models.RecipeWithIngredients
import agalfioni.recipesai.recipe.data.models.SyncableRecipe
import agalfioni.recipesai.recipe.domain.models.Nutrition

fun List<RecipeWithIngredients>.toSyncable(): List<SyncableRecipe> = this.map { it.toSyncable() }

fun RecipeWithIngredients.toSyncable(): SyncableRecipe =
    SyncableRecipe(
        id = recipe.id,
        title = recipe.title,
        difficulty = recipe.difficulty,
        minutesTime = recipe.minutesTime,
        ingredientCoverage = recipe.ingredientCoverage,
        instructions = instructions.map { it.toDomain() },
        nutrition = Nutrition(calories = recipe.calories),
        ingredients = ingredients.map { it.toDomain() },
        ingredientNames = ingredients.map { it.name },
    )
