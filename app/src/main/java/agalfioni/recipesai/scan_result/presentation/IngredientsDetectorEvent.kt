package agalfioni.recipesai.scan_result.presentation

import android.net.Uri

sealed interface IngredientsDetectorEvent {
    data class onImageToAnalyze(val imageUri: Uri) : IngredientsDetectorEvent
    data class onIngredientSelectionChanged(val item: String): IngredientsDetectorEvent
}
