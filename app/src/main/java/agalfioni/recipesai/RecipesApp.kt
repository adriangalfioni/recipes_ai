package agalfioni.recipesai

import agalfioni.recipesai.core.data.ai.di.aiModule
import agalfioni.recipesai.core.data.ingredients.di.ingredientsModule
import agalfioni.recipesai.core.data.recipes.di.recipesModule
import agalfioni.recipesai.core.di.aiGenerativeModelModule
import agalfioni.recipesai.core.di.aiParserModule
import agalfioni.recipesai.core.di.androidLanguageModule
import agalfioni.recipesai.core.di.dbModule
import agalfioni.recipesai.core.di.firebaseModule
import agalfioni.recipesai.core.di.jsonModule
import agalfioni.recipesai.home.di.homeModule
import agalfioni.recipesai.ingredients_detector.presentation.di.ingredientsDetectorModule
import agalfioni.recipesai.recipe.recipe_details.di.recipeDetailsModule
import agalfioni.recipesai.recipe.recipe_list.di.recipeListModule
import agalfioni.recipesai.shared.image_proccessing.di.imageProcessingModule
import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.crashlytics
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
            // Load modules
            modules(
                aiModule,
                aiParserModule,
                jsonModule,
                dbModule,
                androidLanguageModule,
                firebaseModule,
                ingredientsModule,
                recipesModule,
                aiModule,
                aiGenerativeModelModule,
                // Features modules
                recipeDetailsModule,
                homeModule,
                ingredientsDetectorModule,
                recipeListModule,
                imageProcessingModule
            )
        }
    }
}
