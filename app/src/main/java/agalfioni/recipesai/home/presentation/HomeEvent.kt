package agalfioni.recipesai.home.presentation

sealed interface HomeEvent {
    data class OnIngredientRemoved(val item: String): HomeEvent
    data class OnQueryChanged(val query: String): HomeEvent
    data class OnSuggestionSelected(val suggestedItem: String): HomeEvent
    object OnClearAll: HomeEvent
}
