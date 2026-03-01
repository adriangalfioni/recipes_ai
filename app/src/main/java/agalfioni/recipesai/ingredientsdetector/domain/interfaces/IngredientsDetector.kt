package agalfioni.recipesai.ingredientsdetector.domain.interfaces

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError

interface IngredientsDetector {
    suspend fun detectIngredients(
        bytearray: ByteArray,
        prompt: String,
    ): AppResult<String, DataError>
}
