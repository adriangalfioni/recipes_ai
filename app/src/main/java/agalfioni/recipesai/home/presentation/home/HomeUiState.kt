package agalfioni.recipesai.home.presentation.home

import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.recipe_list.presentation.models.RecipeUi

data class HomeUiState(
    val addedIngredients: List<String> = listOf(),
    val allLocalIngredients: List<String> = listOf(),
    val error: DataError? = null,

    val query: String = "",
    val suggestions: List<String> = emptyList(),
    val showSuggestions: Boolean = false,

    val recentRecipes: List<RecipeUi> = emptyList()
)