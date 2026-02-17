package agalfioni.recipesai.recipe.data.sync

import agalfioni.recipesai.recipe.data.models.SyncResult
import agalfioni.recipesai.recipe.domain.interfaces.RecipesSyncRepository
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncRecipesRemoteWorker(
    context: Context,
    params: WorkerParameters,
    private val recipesSyncRepository: RecipesSyncRepository
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result {

        return when (recipesSyncRepository.syncRecipes()) {
            is SyncResult.Success -> Result.success()
            is SyncResult.PartialSuccess -> Result.retry()
            is SyncResult.Error -> Result.retry()
        }
    }
}