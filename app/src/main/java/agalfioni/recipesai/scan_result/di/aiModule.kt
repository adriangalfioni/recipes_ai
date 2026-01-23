package agalfioni.recipesai.scan_result.di

import agalfioni.recipesai.scan_result.data.IngredientsDetectorDataSource
import agalfioni.recipesai.scan_result.data.IngredientsDetectorRepositoryImpl
import agalfioni.recipesai.scan_result.data.utils.ImageProcessor
import agalfioni.recipesai.scan_result.domain.IngredientsDetectorRepository
import com.google.firebase.Firebase
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val aiModule = module {
    // 1. Provide the GenerativeModel
    factory<GenerativeModel> {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel(modelName = "gemini-2.5-flash")
    }

    // 2. Provide the ImageProcessor (Context is handled by androidContext())
    factory { ImageProcessor(androidContext()) }

    // 3. Provide the DataSource
    factory { IngredientsDetectorDataSource(get()) }

    // 4. Provide the Repository
    factory<IngredientsDetectorRepository> { IngredientsDetectorRepositoryImpl(get(), get()) }
}