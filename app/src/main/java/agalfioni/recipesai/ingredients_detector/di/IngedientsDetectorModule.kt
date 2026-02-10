package agalfioni.recipesai.ingredients_detector.di

import agalfioni.recipesai.ingredients_detector.data.data_source.GeminiIngredientsDetectorDataSource
import agalfioni.recipesai.ingredients_detector.data.IngredientsRepositoryImpl
import agalfioni.recipesai.ingredients_detector.data.LoggingImageObserver
import agalfioni.recipesai.ingredients_detector.data.parser.JsonIngredientParser
import agalfioni.recipesai.ingredients_detector.data.utils.ImageProcessorImpl
import agalfioni.recipesai.ingredients_detector.domain.use_cases.DetectIngredientsUseCase
import agalfioni.recipesai.ingredients_detector.domain.interfaces.ImageProcessingObserver
import agalfioni.recipesai.ingredients_detector.domain.interfaces.ImageProcessor
import agalfioni.recipesai.ingredients_detector.domain.interfaces.IngredientsParser
import agalfioni.recipesai.ingredients_detector.domain.interfaces.IngredientsRepository
import agalfioni.recipesai.ingredients_detector.presentation.IngredientsDetectorViewModel
import agalfioni.recipesai.ingredients_detector.data.IngredientsDetectorImpl
import agalfioni.recipesai.ingredients_detector.data.data_source.LocalizedIngredientsDataSource
import agalfioni.recipesai.ingredients_detector.domain.interfaces.IngredientsDetector
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ingredientsModule = module {
    viewModelOf(::IngredientsDetectorViewModel)

    single<ImageProcessingObserver> { LoggingImageObserver(get()) }

    factory<ImageProcessor> { ImageProcessorImpl(androidContext(), get()) }

    single { LocalizedIngredientsDataSource(androidContext().assets) }

    single { GeminiIngredientsDetectorDataSource(get()) }

    factory { DetectIngredientsUseCase(get(), get(), get(), get()) }

    single<IngredientsRepository> { IngredientsRepositoryImpl(get()) }

    single<IngredientsDetector> { IngredientsDetectorImpl(get()) }

    single<IngredientsParser> { JsonIngredientParser() }
}