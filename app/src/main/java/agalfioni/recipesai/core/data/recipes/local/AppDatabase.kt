package agalfioni.recipesai.core.data.recipes.local

import agalfioni.recipesai.core.data.recipes.local.entity.IngredientEntity
import agalfioni.recipesai.core.data.recipes.local.entity.InstructionsEntity
import agalfioni.recipesai.core.data.recipes.local.entity.RecipeEntity
import agalfioni.recipesai.core.data.recipes.local.type_converters.RecipeConverters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecipeEntity::class, IngredientEntity::class, InstructionsEntity::class], version = 1)
@TypeConverters(RecipeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}