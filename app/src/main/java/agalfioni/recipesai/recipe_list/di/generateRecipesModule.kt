package agalfioni.recipesai.recipe_list.di

import agalfioni.recipesai.recipe_list.data.GenerateRecipesRepositoryImpl
import agalfioni.recipesai.recipe_list.data.RecipeGeneratorDataSource
import agalfioni.recipesai.recipe_list.domain.GenerateRecipesRepository
import agalfioni.recipesai.recipe_list.presentation.AiProgressViewModel
import agalfioni.recipesai.recipe_list.presentation.GenerateRecipesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val generateRecipesModule = module {

    single { RecipeGeneratorDataSource(get()) }

    single<GenerateRecipesRepository> { GenerateRecipesRepositoryImpl(get(), get()) }

    viewModelOf(::AiProgressViewModel)
    viewModelOf(::GenerateRecipesViewModel)
}
