package agalfioni.recipesai.recipe.data.local.daos

import agalfioni.recipesai.recipe.data.local.entity.RecipeSyncEntity
import agalfioni.recipesai.recipe.data.models.RecipeWithIngredients
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesSyncDao {

    @Upsert
    suspend fun upsertRecipesSync(recipesSync: List<RecipeSyncEntity>)

    @Transaction
    @Query("""
        SELECT * FROM recipes r 
        INNER JOIN recipes_sync rs ON r.id = rs.recipeId 
        WHERE rs.synced = 0
    """)
    fun getRecipesSync(): Flow<List<RecipeWithIngredients>>

    @Query("DELETE FROM recipes_sync WHERE recipeId IN (:syncableRecipesIds)")
    suspend fun deleteSyncedRecipes(syncableRecipesIds: List<String>)


}
