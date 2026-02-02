package agalfioni.recipesai

import agalfioni.recipesai.core.di.aiModule
import agalfioni.recipesai.core.di.aiParserModule
import agalfioni.recipesai.core.di.androidLanguageModule
import agalfioni.recipesai.core.di.dbModule
import agalfioni.recipesai.core.di.jsonModule
import agalfioni.recipesai.home.di.homeModule
import agalfioni.recipesai.recipe_details.di.recipeDetailsModule
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
                aiModule,
                aiParserModule,
                jsonModule,
                dbModule,
                androidLanguageModule,
                // Features modules
                recipeDetailsModule,
                homeModule,
                generateRecipesModule,
            )
        }
    }
}
