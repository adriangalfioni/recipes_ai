package agalfioni.recipesai.home.presentation.scan_result

import android.net.Uri

sealed interface IngredientsDetectorEvent {
    data class OnImageToAnalyze(val imageUri: Uri) : IngredientsDetectorEvent
    data class OnIngredientSelectionChanged(val item: String): IngredientsDetectorEvent
    data class OnQueryChanged(val query: String): IngredientsDetectorEvent
    data class OnSuggestionSelected(val suggestedItem: String): IngredientsDetectorEvent
}
