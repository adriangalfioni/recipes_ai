package agalfioni.recipesai.recipe.data.local.database

import agalfioni.recipesai.recipe.data.local.daos.RecipeDao
import agalfioni.recipesai.recipe.data.local.daos.RecipesSyncDao
import agalfioni.recipesai.recipe.data.local.entity.IngredientEntity
import agalfioni.recipesai.recipe.data.local.entity.InstructionsEntity
import agalfioni.recipesai.recipe.data.local.entity.RecipeEntity
import agalfioni.recipesai.recipe.data.local.entity.RecipeSyncEntity
import agalfioni.recipesai.recipe.data.local.typeconverters.RecipeConverters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        RecipeEntity::class,
        IngredientEntity::class,
        InstructionsEntity::class,
        RecipeSyncEntity::class,
    ],
    version = 2,
    exportSchema = true,
)
@TypeConverters(RecipeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    abstract fun recipesSyncDao(): RecipesSyncDao
}
