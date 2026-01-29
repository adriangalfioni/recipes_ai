package agalfioni.recipesai.recipe_details.presentation.di

import agalfioni.recipesai.recipe_details.data.RecipeDetailsRepositoryImpl
import agalfioni.recipesai.recipe_details.domain.RecipeDetailsRepository
import agalfioni.recipesai.recipe_details.presentation.RecipeDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val recipeDetailsModule = module {

    single<RecipeDetailsRepository> { RecipeDetailsRepositoryImpl(get()) }

    viewModelOf(::RecipeDetailsViewModel)
}
