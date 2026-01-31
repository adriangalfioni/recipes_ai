package agalfioni.recipesai.home.di

import agalfioni.recipesai.core.data.repository.LocalIngredientsLoader
import agalfioni.recipesai.home.data.IngredientsDetectorDataSource
import agalfioni.recipesai.home.data.IngredientsDetectorRepositoryImpl
import agalfioni.recipesai.home.data.utils.ImageProcessor
import agalfioni.recipesai.home.domain.IngredientsDetectorRepository
import agalfioni.recipesai.home.presentation.home.HomeViewModel
import agalfioni.recipesai.home.presentation.scan_result.IngredientsDetectorViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::IngredientsDetectorViewModel)
    viewModelOf(::HomeViewModel)

    factory { ImageProcessor(androidContext()) }

    single { IngredientsDetectorDataSource(get()) }

    single { LocalIngredientsLoader(androidContext().assets) }

    single<IngredientsDetectorRepository> { IngredientsDetectorRepositoryImpl(get(), get(), get()) }
}