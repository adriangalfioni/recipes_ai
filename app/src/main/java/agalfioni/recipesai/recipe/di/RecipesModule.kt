package agalfioni.recipesai.recipe.di

import agalfioni.recipesai.recipe.data.remote.AiRecipeGeneratorDataSource
import agalfioni.recipesai.recipe.data.remote.AiRecipeGeneratorImpl
import agalfioni.recipesai.recipe.data.repository.RecipeRepositoryImpl
import agalfioni.recipesai.recipe.data.repository.RecipesSyncRepositoryImpl
import agalfioni.recipesai.recipe.domain.GenerationTracker
import agalfioni.recipesai.recipe.domain.interfaces.AiRecipeGenerator
import agalfioni.recipesai.recipe.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe.domain.interfaces.RecipesSyncRepository
import agalfioni.recipesai.recipe.domain.usecase.GenerateRecipesUseCase
import agalfioni.recipesai.recipe.presentation.recipedetails.RecipeDetailsViewModel
import agalfioni.recipesai.recipe.presentation.recipelist.AiProgressViewModel
import agalfioni.recipesai.recipe.presentation.recipelist.RecipesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val recipesModule =
    module {

        single { AiRecipeGeneratorDataSource(get()) }

        single<AiRecipeGenerator> { AiRecipeGeneratorImpl(get(), get(), get()) }

        single<RecipeRepository> { RecipeRepositoryImpl(get()) }

        single<RecipesSyncRepository> { RecipesSyncRepositoryImpl(get(), get()) }

        single { GenerationTracker() }

        factory<GenerateRecipesUseCase> { GenerateRecipesUseCase(get(), get(), get(), get()) }

        viewModelOf(::AiProgressViewModel)
        viewModelOf(::RecipesListViewModel)
        viewModelOf(::RecipeDetailsViewModel)
    }
