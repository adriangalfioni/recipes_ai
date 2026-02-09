package agalfioni.recipesai.core.data.ai.di

import agalfioni.recipesai.core.ai.domain.detector.AiIngredientsDetector
import agalfioni.recipesai.core.ai.domain.generator.AiRecipeGenerator
import agalfioni.recipesai.core.data.ai.AiRecipeGeneratorImpl
import agalfioni.recipesai.core.data.ai.GeminiIngredientsDetector
import agalfioni.recipesai.core.data.ai.data_source.AiRecipeGeneratorDataSource
import agalfioni.recipesai.core.data.ai.data_source.IngredientsDetectorDataSource
import org.koin.dsl.module

val aiModule = module {

    single { AiRecipeGeneratorDataSource(get()) }

    single<AiRecipeGenerator> { AiRecipeGeneratorImpl(get(), get(), get()) }

    single { IngredientsDetectorDataSource(get()) }

    single<AiIngredientsDetector> { GeminiIngredientsDetector(get()) }

}