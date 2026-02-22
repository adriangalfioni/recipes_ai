package agalfioni.recipesai

import agalfioni.recipesai.core.di.aiModule
import agalfioni.recipesai.core.di.aiParserModule
import agalfioni.recipesai.core.di.androidLanguageModule
import agalfioni.recipesai.core.di.dbModule
import agalfioni.recipesai.core.di.firebaseModule
import agalfioni.recipesai.core.di.jsonModule
import agalfioni.recipesai.core.di.workManagerModule
import agalfioni.recipesai.home.di.homeModule
import agalfioni.recipesai.ingredients_detector.di.ingredientsModule
import agalfioni.recipesai.recipe.data.sync.RecipeDailySyncScheduler
import agalfioni.recipesai.recipe.di.networkModule
import agalfioni.recipesai.recipe.di.recipesModule
import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.crashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.GlobalContext.startKoin
import org.koin.java.KoinJavaComponent.getKoin

class RecipesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // 1. Initialize Firebase first
        FirebaseApp.initializeApp(this)

        // Optional safety check
        checkNotNull(FirebaseApp.getInstance())

        // Disable Crashlytics collection in Debug mode
        if (BuildConfig.DEBUG) {
            Firebase.crashlytics.isCrashlyticsCollectionEnabled = false
        } else {
            Firebase.crashlytics.isCrashlyticsCollectionEnabled = true
        }

        startKoin {
            // Log Koin errors/info
            androidLogger()
            // Reference Android context
            androidContext(this@RecipesApp)
            workManagerFactory()
            // Load modules
            modules(
                aiModule,
                aiParserModule,
                jsonModule,
                dbModule,
                androidLanguageModule,
                firebaseModule,
                workManagerModule,
                networkModule,
                // Features modules
                homeModule,
                recipesModule,
                ingredientsModule
            )
        }

        val scheduler: RecipeDailySyncScheduler = getKoin().get<RecipeDailySyncScheduler>()
        scheduler.schedule()
    }
}
