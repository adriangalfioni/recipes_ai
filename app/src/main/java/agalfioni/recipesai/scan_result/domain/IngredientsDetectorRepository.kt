package agalfioni.recipesai.scan_result.domain

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import android.net.Uri

interface IngredientsDetectorRepository {
    suspend fun analyzeFridge(uri: Uri): AppResult<IngredientsResult, DataError>
}