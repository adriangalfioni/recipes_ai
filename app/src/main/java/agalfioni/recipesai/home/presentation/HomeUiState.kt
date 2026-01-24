package agalfioni.recipesai.home.presentation

import agalfioni.recipesai.core.domain.models.DataError

data class HomeUiState(
    val isLoading: Boolean = false,
    val addedIngredients: MutableList<String> = mutableListOf(),
    val allLocalIngredients: List<String> = listOf(),
    val error: DataError? = null,

    val query: String = "",
    val suggestions: List<String> = emptyList(),
    val showSuggestions: Boolean = false
)