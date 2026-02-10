package agalfioni.recipesai.recipe.data.local

import agalfioni.recipesai.recipe.data.local.entity.IngredientEntity
import agalfioni.recipesai.recipe.data.local.entity.InstructionsEntity
import agalfioni.recipesai.recipe.data.local.entity.RecipeEntity
import agalfioni.recipesai.recipe.data.local.type_converters.RecipeConverters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecipeEntity::class, IngredientEntity::class, InstructionsEntity::class], version = 1)
@TypeConverters(RecipeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}