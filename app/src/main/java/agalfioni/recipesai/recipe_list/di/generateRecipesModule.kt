package agalfioni.recipesai.recipe_list.di

import agalfioni.recipesai.recipe_list.data.FakeGenerateRecipesRepositoryImpl
import agalfioni.recipesai.recipe_list.data.RecipeGeneratorDataSource
import agalfioni.recipesai.recipe_list.domain.GenerateRecipesRepository
import agalfioni.recipesai.recipe_list.presentation.AiProgressViewModel
import agalfioni.recipesai.recipe_list.presentation.RecipesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val generateRecipesModule = module {

    single { RecipeGeneratorDataSource(get()) }

    //single<GenerateRecipesRepository> { GenerateRecipesRepositoryImpl(get(), get()) }
    single<GenerateRecipesRepository> { FakeGenerateRecipesRepositoryImpl(get()) }

    viewModelOf(::AiProgressViewModel)
    viewModelOf(::RecipesListViewModel)
}
