package agalfioni.recipesai.recipe_list.di

import agalfioni.recipesai.recipe_list.data.remote.AiRecipeGeneratorDataSource
import agalfioni.recipesai.recipe_list.data.remote.AiRecipeGeneratorImpl
import agalfioni.recipesai.recipe_list.data.repository.RecipeRepositoryImpl
import agalfioni.recipesai.recipe_list.domain.GenerationTracker
import agalfioni.recipesai.recipe_list.domain.interfaces.AiRecipeGenerator
import agalfioni.recipesai.recipe_list.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe_list.domain.usecase.GenerateRecipesUseCase
import agalfioni.recipesai.recipe_list.presentation.AiProgressViewModel
import agalfioni.recipesai.recipe_list.presentation.RecipesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val generateRecipesModule = module {

    single { AiRecipeGeneratorDataSource(get()) }

    single<AiRecipeGenerator> { AiRecipeGeneratorImpl(get(), get()) }

    single<RecipeRepository> { RecipeRepositoryImpl(get()) }

    single { GenerationTracker() }

    single<GenerateRecipesUseCase> { GenerateRecipesUseCase(get(), get(), get()) }

    viewModelOf(::AiProgressViewModel)
    viewModelOf(::RecipesListViewModel)
}
