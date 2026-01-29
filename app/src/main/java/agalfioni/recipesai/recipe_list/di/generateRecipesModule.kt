package agalfioni.recipesai.recipe_list.di

import agalfioni.recipesai.recipe_list.data.repository.FakeGenerateRecipesRepositoryImpl
import agalfioni.recipesai.recipe_list.data.remote.AiRecipeGeneratorDataSource
import agalfioni.recipesai.recipe_list.data.repository.GenerateRecipesRepositoryImpl
import agalfioni.recipesai.recipe_list.domain.GenerateRecipesRepository
import agalfioni.recipesai.recipe_list.presentation.AiProgressViewModel
import agalfioni.recipesai.recipe_list.presentation.RecipesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val generateRecipesModule = module {

    single { AiRecipeGeneratorDataSource(get()) }

    single<GenerateRecipesRepository> { GenerateRecipesRepositoryImpl(get(), get(), get()) }
    //single<GenerateRecipesRepository> { FakeGenerateRecipesRepositoryImpl(get()) }

    viewModelOf(::AiProgressViewModel)
    viewModelOf(::RecipesListViewModel)
}
