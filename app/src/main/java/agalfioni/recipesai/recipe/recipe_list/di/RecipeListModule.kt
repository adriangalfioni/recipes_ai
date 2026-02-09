package agalfioni.recipesai.recipe.recipe_list.di

import agalfioni.recipesai.recipe.recipe_list.presentation.AiProgressViewModel
import agalfioni.recipesai.recipe.recipe_list.presentation.RecipesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val recipeListModule = module {

    viewModelOf(::AiProgressViewModel)
    viewModelOf(::RecipesListViewModel)
}
