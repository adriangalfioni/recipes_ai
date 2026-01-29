package agalfioni.recipesai.recipe_list.data.local

import agalfioni.recipesai.recipe_list.data.local.entity.IngredientEntity
import agalfioni.recipesai.recipe_list.data.local.entity.InstructionsEntity
import agalfioni.recipesai.recipe_list.data.local.entity.RecipeEntity
import agalfioni.recipesai.recipe_list.data.models.RecipeWithIngredients
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    @Insert
    suspend fun insertIngredients(ingredients: List<IngredientEntity>)

    @Insert
    suspend fun insertInstructions(instructions: List<InstructionsEntity>)

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :recipeId LIMIT 1")
    fun getRecipeById(recipeId: String): Flow<RecipeWithIngredients?>

    // Helper to save the whole object at once
    @Transaction
    suspend fun saveFullRecipe(recipe: Recipe) {
        val recipeEntity = RecipeEntity(
            id = recipe.id,
            title = recipe.title,
            difficulty = recipe.difficulty,
            minutesTime = recipe.minutesTime,
            ingredientCoverage = recipe.ingredientCoverage,
            calories = recipe.nutrition.calories
        )
        insertRecipe(recipeEntity)

        val ingredients = recipe.ingredients.map {
            IngredientEntity(
                recipeId = recipe.id,
                name = it.name,
                quantity = it.quantity,
                unit = it.unit
            )
        }
        insertIngredients(ingredients)

        val instructions = recipe.instructions.map {
            InstructionsEntity(
                recipeId = recipe.id,
                title = it.title,
                description = it.description
            )
        }
        insertInstructions(instructions)
    }
}