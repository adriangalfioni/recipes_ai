package agalfioni.recipesai.recipe.presentation.recipedetails

import agalfioni.recipesai.recipe.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe.domain.models.RecipeDetailsError
import agalfioni.recipesai.recipe.presentation.recipedetails.mappers.asUiText
import agalfioni.recipesai.recipe.presentation.recipedetails.mappers.toRecipeDetailsUi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RecipeDetailsViewModel(
    recipeId: String,
    repository: RecipeRepository,
) : ViewModel() {
    val uiState: StateFlow<RecipeDetailsState> =
        repository
            .getRecipeById(recipeId)
            .map { domainRecipe ->
                RecipeDetailsState(
                    isLoading = false,
                    recipeDetailsUi = domainRecipe?.toRecipeDetailsUi(),
                    error = if (domainRecipe == null) RecipeDetailsError.RECIPE_NOT_FOUND.asUiText() else null,
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = RecipeDetailsState(isLoading = true),
            )
}
