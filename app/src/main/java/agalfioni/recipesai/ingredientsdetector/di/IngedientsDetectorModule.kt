package agalfioni.recipesai.ingredientsdetector.di

import agalfioni.recipesai.ingredientsdetector.data.IngredientsDetectorImpl
import agalfioni.recipesai.ingredientsdetector.data.IngredientsRepositoryImpl
import agalfioni.recipesai.ingredientsdetector.data.LoggingImageObserver
import agalfioni.recipesai.ingredientsdetector.data.datasource.GeminiIngredientsDetectorDataSource
import agalfioni.recipesai.ingredientsdetector.data.datasource.LocalizedIngredientsDataSource
import agalfioni.recipesai.ingredientsdetector.data.parser.JsonIngredientParser
import agalfioni.recipesai.ingredientsdetector.data.utils.ImageProcessorImpl
import agalfioni.recipesai.ingredientsdetector.domain.interfaces.ImageProcessingObserver
import agalfioni.recipesai.ingredientsdetector.domain.interfaces.ImageProcessor
import agalfioni.recipesai.ingredientsdetector.domain.interfaces.IngredientsDetector
import agalfioni.recipesai.ingredientsdetector.domain.interfaces.IngredientsParser
import agalfioni.recipesai.ingredientsdetector.domain.interfaces.IngredientsRepository
import agalfioni.recipesai.ingredientsdetector.domain.usecases.DetectIngredientsUseCase
import agalfioni.recipesai.ingredientsdetector.presentation.IngredientsDetectorViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ingredientsModule =
    module {
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
