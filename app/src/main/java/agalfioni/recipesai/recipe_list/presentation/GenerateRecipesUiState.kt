package agalfioni.recipesai.recipe_list.presentation

import agalfioni.recipesai.core.presentation.utils.UiText
import agalfioni.recipesai.recipe_list.domain.models.Recipe

data class GenerateRecipesUiState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val error: UiText? = null
)
