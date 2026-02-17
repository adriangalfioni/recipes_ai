package agalfioni.recipesai.recipe.data.sync

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class RecipeDailySyncScheduler(
    private val workManager: WorkManager
) {

    companion object {
        private const val SYNC_WORK_NAME = "daily_recipe_sync_task"
        private const val MANUAL_SYNC_NAME = "manual_recipe_sync_task"
    }

    fun schedule() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED) // Wi-Fi
            .setRequiresBatteryNotLow(true)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<SyncRecipesRemoteWorker>(
            24, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

    fun syncNow() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Any connection (not just Wi-Fi)
            .build()

        val oneTimeRequest = OneTimeWorkRequestBuilder<SyncRecipesRemoteWorker>()
            .setConstraints(constraints)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST) // Try to run immediately
            .addTag(MANUAL_SYNC_NAME)
            .build()

        workManager.enqueueUniqueWork(
            MANUAL_SYNC_NAME,
            ExistingWorkPolicy.REPLACE, // Start a fresh one immediately
            oneTimeRequest
        )
    }
}