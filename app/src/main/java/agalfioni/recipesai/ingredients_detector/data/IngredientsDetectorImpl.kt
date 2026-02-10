package agalfioni.recipesai.ingredients_detector.data

import agalfioni.recipesai.core.data.helpers.safeAiCall
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.ingredients_detector.data.data_source.GeminiIngredientsDetectorDataSource
import agalfioni.recipesai.ingredients_detector.domain.interfaces.IngredientsDetector

class IngredientsDetectorImpl(
    private val aiRemoteDataSource: GeminiIngredientsDetectorDataSource,
): IngredientsDetector {

    override suspend fun detectIngredients(
        bytearray: ByteArray,
        prompt: String
    ): AppResult<String, DataError> {
        return safeAiCall {
            // Call AI with specific prompt
            aiRemoteDataSource.generateContent(
                compressedBytes = bytearray,
                prompt = prompt
            )
        }
    }
}