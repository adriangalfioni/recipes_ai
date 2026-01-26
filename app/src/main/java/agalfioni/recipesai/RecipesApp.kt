package agalfioni.recipesai

import agalfioni.recipesai.core.di.appModule
import agalfioni.recipesai.home.di.aiModule
import agalfioni.recipesai.home.di.aiParserModule
import agalfioni.recipesai.home.di.jsonModule
import agalfioni.recipesai.recipe_list.di.generateRecipesModule
import android.app.Application
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class RecipesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // 1. Initialize Firebase first
        FirebaseApp.initializeApp(this)

        // Optional safety check
        checkNotNull(FirebaseApp.getInstance())

        startKoin {
            // Log Koin errors/info
            androidLogger()
            // Reference Android context
            androidContext(this@RecipesApp)
            // Load modules
            modules(
                appModule,
                aiModule,
                aiParserModule,
                generateRecipesModule,
                jsonModule
            )
        }
    }
}
