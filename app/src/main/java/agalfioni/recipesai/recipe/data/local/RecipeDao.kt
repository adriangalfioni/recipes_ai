package agalfioni.recipesai.recipe.data.local

import agalfioni.recipesai.recipe.data.local.entity.IngredientEntity
import agalfioni.recipesai.recipe.data.local.entity.InstructionsEntity
import agalfioni.recipesai.recipe.data.local.entity.RecipeEntity
import agalfioni.recipesai.recipe.data.models.RecipeWithIngredients
import agalfioni.recipesai.recipe.domain.models.Recipe
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Upsert
    suspend fun insertRecipes(recipes: List<RecipeEntity>)

    @Upsert
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    @Upsert
    suspend fun insertIngredients(ingredients: List<IngredientEntity>)

    @Upsert
    suspend fun insertInstructions(instructions: List<InstructionsEntity>)

    @Transaction
    @Query("SELECT * FROM recipes WHERE id = :recipeId LIMIT 1")
    fun getRecipeById(recipeId: String): Flow<RecipeWithIngredients?>

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeWithIngredients>>

    @Transaction
    suspend fun saveFullRecipes(recipes: List<Recipe>) {

        val recipeEntities = recipes.map { recipe ->
            RecipeEntity(
                id = recipe.id,
                title = recipe.title,
                difficulty = recipe.difficulty,
                minutesTime = recipe.minutesTime,
                ingredientCoverage = recipe.ingredientCoverage,
                calories = recipe.nutrition.calories
            )
        }

        val ingredientEntities = recipes.flatMap { recipe ->
            recipe.ingredients.map {
                IngredientEntity(
                    recipeId = recipe.id,
                    name = it.name,
                    quantity = it.quantity,
                    unit = it.unit
                )
            }
        }

        val instructionEntities = recipes.flatMap { recipe ->
            recipe.instructions.map {
                InstructionsEntity(
                    recipeId = recipe.id,
                    title = it.title,
                    description = it.description
                )
            }
        }

        insertRecipes(recipeEntities)
        insertIngredients(ingredientEntities)
        insertInstructions(instructionEntities)
    }

}