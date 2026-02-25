package agalfioni.recipesai.home.presentation.home

import agalfioni.recipesai.core.domain.models.DataError

data class HomeUiState(
    val addedIngredients: List<String> = listOf(),
    val allLocalIngredients: List<String> = listOf(),
    val error: DataError? = null,

    val query: String = "",
    val suggestions: List<String> = emptyList(),
    val showSuggestions: Boolean = false,
)