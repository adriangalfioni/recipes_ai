package agalfioni.recipesai.scan_result.presentation

import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.presentation.models.Selectable
import android.net.Uri

data class IngredientsDetectorUiState(
    val imageUri: Uri,
    val isLoading: Boolean = false,
    val detectedIngredients: List<Selectable<String>> = listOf(),
    val allLocalIngredients: List<String> = listOf(),
    val error: DataError? = null,

    val query: String = "",
    val suggestions: List<String> = emptyList(),
    val showSuggestions: Boolean = false
)