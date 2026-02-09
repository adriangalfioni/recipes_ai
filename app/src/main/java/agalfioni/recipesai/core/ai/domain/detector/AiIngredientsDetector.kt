package agalfioni.recipesai.core.ai.domain.detector

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError

interface AiIngredientsDetector {
    suspend fun detectIngredients(bytearray: ByteArray, prompt: String): AppResult<String, DataError>
}