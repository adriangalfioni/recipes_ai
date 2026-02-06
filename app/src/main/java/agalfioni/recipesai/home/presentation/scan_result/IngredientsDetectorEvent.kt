package agalfioni.recipesai.home.presentation.scan_result

sealed interface IngredientsDetectorEvent {
    data class OnIngredientSelectionChanged(val item: String): IngredientsDetectorEvent
    data class OnQueryChanged(val query: String): IngredientsDetectorEvent
    data class OnSuggestionSelected(val suggestedItem: String): IngredientsDetectorEvent
}
