package agalfioni.recipesai.scan_result.presentation

import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.presentation.models.Selectable
import android.net.Uri

data class IngredientsDetectorUiState(
    val imageUri: Uri,
    val isLoading: Boolean = false,
    val ingredients: List<Selectable<String>> = listOf(),
    val error: DataError? = null
)