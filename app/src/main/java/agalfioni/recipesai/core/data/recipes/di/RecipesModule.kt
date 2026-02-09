package agalfioni.recipesai.core.data.recipes.di

import agalfioni.recipesai.core.data.recipes.repository.RecipeRepositoryImpl
import agalfioni.recipesai.core.recipes.domain.interfaces.RecipeRepository
import agalfioni.recipesai.core.recipes.domain.use_case.GenerateRecipesUseCase
import agalfioni.recipesai.core.recipes.domain.use_case.GenerationTracker
import org.koin.dsl.module

val recipesModule = module {

    single<RecipeRepository> { RecipeRepositoryImpl(get()) }

    single { GenerationTracker() }

    factory<GenerateRecipesUseCase> { GenerateRecipesUseCase(get(), get(), get()) }

}
