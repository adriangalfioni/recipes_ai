package agalfioni.recipesai.recipe_list.di

import agalfioni.recipesai.recipe_list.data.GenerateRecipesRepositoryImpl
import agalfioni.recipesai.recipe_list.data.RecipeGeneratorDataSource
import agalfioni.recipesai.recipe_list.domain.GenerateRecipesRepository
import org.koin.dsl.module

val generateRecipesModule = module {

    single { RecipeGeneratorDataSource(get()) }

    single<GenerateRecipesRepository> { GenerateRecipesRepositoryImpl(get(), get()) }
}
