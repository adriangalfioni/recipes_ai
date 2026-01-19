package agalfioni.recipesai

import agalfioni.recipesai.core.di.appModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class RecipesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin errors/info
            androidLogger()
            // Reference Android context
            androidContext(this@RecipesApp)
            // Load modules
            modules(appModule)
        }
    }
}
