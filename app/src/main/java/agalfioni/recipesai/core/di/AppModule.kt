package agalfioni.recipesai.core.di

import agalfioni.recipesai.home.presentation.home.HomeViewModel
import agalfioni.recipesai.home.presentation.scan_result.IngredientsDetectorViewModel
import agalfioni.recipesai.recipe_list.presentation.AiProgressViewModel
import agalfioni.recipesai.recipe_list.presentation.GenerateRecipesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::IngredientsDetectorViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::AiProgressViewModel)
    viewModelOf(::GenerateRecipesViewModel)
}