package agalfioni.recipesai.core.data.ai

import agalfioni.recipesai.core.ai.domain.detector.AiIngredientsDetector
import agalfioni.recipesai.core.data.helpers.safeAiCall
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.data.ai.data_source.IngredientsDetectorDataSource

class GeminiIngredientsDetector(
    private val aiRemoteDataSource: IngredientsDetectorDataSource,
): AiIngredientsDetector {

    override suspend fun detectIngredients(bytearray: ByteArray, prompt: String): AppResult<String, DataError> {
        return safeAiCall {
            // Call AI with specific prompt
            aiRemoteDataSource.generateContent(
                compressedBytes = bytearray,
                prompt = prompt
            )
        }
    }
}