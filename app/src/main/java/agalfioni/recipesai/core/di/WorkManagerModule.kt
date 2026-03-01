package agalfioni.recipesai.core.di

import agalfioni.recipesai.recipe.data.sync.RecipeDailySyncScheduler
import agalfioni.recipesai.recipe.data.sync.SyncRecipesRemoteWorker
import androidx.work.WorkManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workManagerModule =
    module {

        single { WorkManager.getInstance(androidContext()) }

        single { RecipeDailySyncScheduler(get()) }

        worker { SyncRecipesRemoteWorker(get(), get(), get()) }
    }
