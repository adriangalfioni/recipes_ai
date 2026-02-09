package agalfioni.recipesai.recipe.recipe_details.di

import agalfioni.recipesai.recipe.recipe_details.presentation.RecipeDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val recipeDetailsModule = module {

    viewModelOf(::RecipeDetailsViewModel)
}
