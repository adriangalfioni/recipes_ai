package agalfioni.recipesai.ingredients_detector.presentation.di

import agalfioni.recipesai.ingredients_detector.presentation.IngredientsDetectorViewModel
import agalfioni.recipesai.recipe.recipe_details.presentation.RecipeDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ingredientsDetectorModule = module {

    viewModelOf(::IngredientsDetectorViewModel)
}
