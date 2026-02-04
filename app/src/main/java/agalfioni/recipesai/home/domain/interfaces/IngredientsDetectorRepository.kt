package agalfioni.recipesai.home.domain.interfaces

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.LocalIngredient
import agalfioni.recipesai.home.domain.IngredientsResult
import android.net.Uri

interface IngredientsDetectorRepository {
    suspend fun analyzeFridge(uri: Uri): AppResult<IngredientsResult, DataError>
    suspend fun getLocalIngredients(): Result<List<LocalIngredient>>
}