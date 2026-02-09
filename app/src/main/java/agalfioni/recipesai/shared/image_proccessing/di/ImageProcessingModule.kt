package agalfioni.recipesai.shared.image_proccessing.di

import agalfioni.recipesai.shared.image_proccessing.data.ImageProcessorImpl
import agalfioni.recipesai.shared.image_proccessing.data.LoggingImageObserver
import agalfioni.recipesai.shared.image_proccessing.domain.ImageProcessingObserver
import agalfioni.recipesai.shared.image_proccessing.domain.ImageProcessor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val imageProcessingModule = module {

    single<ImageProcessingObserver> { LoggingImageObserver(get()) }

    factory<ImageProcessor> { ImageProcessorImpl(androidContext(), get()) }

}