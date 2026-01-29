package agalfioni.recipesai.recipe_list.data.local.mappers

import agalfioni.recipesai.recipe_list.data.local.entity.IngredientEntity
import agalfioni.recipesai.recipe_list.data.local.entity.InstructionsEntity
import agalfioni.recipesai.recipe_list.data.models.RecipeWithIngredients
import agalfioni.recipesai.recipe_list.domain.models.Nutrition
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import agalfioni.recipesai.recipe_list.domain.models.RecipeIngredient
import agalfioni.recipesai.recipe_list.domain.models.RecipeInstruction


fun RecipeWithIngredients.toDomain(): Recipe {
    return Recipe(
        id = recipe.id,
        title = recipe.title,
        difficulty = recipe.difficulty,
        minutesTime = recipe.minutesTime,
        ingredientCoverage = recipe.ingredientCoverage,
        instructions = instructions.map { it.toDomain() },
        nutrition = Nutrition(calories = recipe.calories),
        ingredients = ingredients.map { it.toDomain() }
    )
}

fun IngredientEntity.toDomain(): RecipeIngredient {
    return RecipeIngredient(
        name = this.name,
        quantity = this.quantity,
        unit = this.unit
    )
}

fun InstructionsEntity.toDomain(): RecipeInstruction {
    return RecipeInstruction(
        title = this.title,
        description = this.description
    )
}
