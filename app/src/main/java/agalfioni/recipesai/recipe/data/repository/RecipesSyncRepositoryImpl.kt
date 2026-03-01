package agalfioni.recipesai.recipe.data.repository

import agalfioni.recipesai.BuildConfig
import agalfioni.recipesai.recipe.data.local.daos.RecipesSyncDao
import agalfioni.recipesai.recipe.data.local.entity.RecipeSyncEntity
import agalfioni.recipesai.recipe.data.local.mappers.toSyncable
import agalfioni.recipesai.recipe.data.models.SyncResult
import agalfioni.recipesai.recipe.data.models.SyncableRecipe
import agalfioni.recipesai.recipe.domain.interfaces.RecipesSyncRepository
import agalfioni.recipesai.recipe.domain.models.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RecipesSyncRepositoryImpl(
    private val recipeSyncDao: RecipesSyncDao,
    private val firestore: FirebaseFirestore,
) : RecipesSyncRepository {
    companion object {
        val SYNC_RECIPES_PATH = if (BuildConfig.DEBUG) "recipes_sync_debug" else "recipes_sync"
    }

    override suspend fun save(recipes: List<Recipe>) {
        recipeSyncDao.upsertRecipesSync(
            recipes.map {
                RecipeSyncEntity(
                    recipeId = it.id,
                )
            },
        )
    }

    override suspend fun syncRecipes(): SyncResult {
        val pendingRecipes = recipeSyncDao.getRecipesSync().first()
        if (pendingRecipes.isEmpty()) return SyncResult.Success(0)

        val syncableRecipes = pendingRecipes.toSyncable()
        val syncedRecipeIds = uploadIndependentRecipes(syncableRecipes)

        if (syncedRecipeIds.isNotEmpty()) {
            withContext(NonCancellable) {
                recipeSyncDao.deleteSyncedRecipes(syncedRecipeIds)
            }
        }

        return when {
            syncedRecipeIds.size == pendingRecipes.size -> SyncResult.Success(syncedRecipeIds.size)
            syncedRecipeIds.isEmpty() -> SyncResult.Error("Total failure")
            else -> SyncResult.PartialSuccess(syncedRecipeIds.size, pendingRecipes.size)
        }
    }

    suspend fun uploadIndependentRecipes(syncableRecipes: List<SyncableRecipe>): List<String> =
        supervisorScope {
            syncableRecipes
                .map { syncableRecipe ->
                    async {
                        runCatching {
                            firestore
                                .collection(SYNC_RECIPES_PATH)
                                .document(syncableRecipe.id)
                                .set(syncableRecipe)
                                .await()
                            syncableRecipe.id // Return ID on success
                        }
                    }
                }.awaitAll()
                .mapNotNull { it.getOrNull() }
        }
}
