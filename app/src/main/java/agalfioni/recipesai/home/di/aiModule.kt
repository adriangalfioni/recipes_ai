package agalfioni.recipesai.home.di

import agalfioni.recipesai.core.data.repository.LocalIngredientsLoader
import agalfioni.recipesai.home.data.IngredientsDetectorDataSource
import agalfioni.recipesai.home.data.IngredientsDetectorRepositoryImpl
import agalfioni.recipesai.home.data.utils.ImageProcessor
import agalfioni.recipesai.home.domain.IngredientsDetectorRepository
import com.google.firebase.Firebase
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val aiModule = module {
    factory<GenerativeModel> {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel(modelName = "gemini-2.5-flash")
    }

    factory { ImageProcessor(androidContext()) }

    factory { IngredientsDetectorDataSource(get()) }

    factory { LocalIngredientsLoader(androidContext().assets) }

    factory<IngredientsDetectorRepository> { IngredientsDetectorRepositoryImpl(get(), get(), get()) }
}