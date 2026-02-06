package agalfioni.recipesai.home.di

import agalfioni.recipesai.core.data.repository.LocalIngredientsLoader
import agalfioni.recipesai.home.data.IngredientsDetectorDataSource
import agalfioni.recipesai.home.data.IngredientsRepositoryImpl
import agalfioni.recipesai.home.data.LoggingImageObserver
import agalfioni.recipesai.home.data.parser.JsonIngredientParser
import agalfioni.recipesai.home.data.utils.ImageProcessor
import agalfioni.recipesai.home.domain.DetectIngredientsUseCase
import agalfioni.recipesai.home.domain.interfaces.ImageProcessingObserver
import agalfioni.recipesai.home.domain.interfaces.IngredientsParser
import agalfioni.recipesai.home.domain.interfaces.IngredientsRepository
import agalfioni.recipesai.home.presentation.home.HomeViewModel
import agalfioni.recipesai.home.presentation.scan_result.IngredientsDetectorViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::IngredientsDetectorViewModel)
    viewModelOf(::HomeViewModel)

    single<ImageProcessingObserver> { LoggingImageObserver(get()) }

    factory { ImageProcessor(androidContext(), get()) }

    single { IngredientsDetectorDataSource(get()) }

    single { LocalIngredientsLoader(androidContext().assets) }

    factory { DetectIngredientsUseCase(get(), get(), get(), get()) }

    single<IngredientsRepository> { IngredientsRepositoryImpl(get(), get()) }

    single<IngredientsParser> { JsonIngredientParser() }
}