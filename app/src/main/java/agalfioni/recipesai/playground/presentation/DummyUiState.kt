package agalfioni.recipesai.playground.presentation

import agalfioni.recipesai.core.presentation.utils.UiText
import agalfioni.recipesai.recipe.presentation.recipe_list.models.RecipeUi

data class DummyUiState(
    val isLoading: Boolean = false,
    val listRecipes: List<RecipeUi> = emptyList(),
    val error: UiText? = null
)
