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

    companion object {
        const val MAX_RETRIES = 3
    }

    override suspend fun doWork(): Result {
        return when (recipesSyncRepository.syncRecipes()) {
            is SyncResult.Success -> Result.success()
            is SyncResult.PartialSuccess -> retryWhenMaxNotReached()
            is SyncResult.Error -> retryWhenMaxNotReached()
        }
    }

    private fun retryWhenMaxNotReached(): Result {
        return if (runAttemptCount < MAX_RETRIES) {
            Result.retry()
        } else {
            Result.failure()
        }
    }
}