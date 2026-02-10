package agalfioni.recipesai.ingredients_detector.data

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.ingredients_detector.domain.interfaces.IngredientsDetector

class FakeIngredientsDetector: IngredientsDetector {

    var ingredientsDetectionResult = IngredientDetectionResult.SUCCESS_NON_EMPTY

    override suspend fun detectIngredients(
        bytearray: ByteArray,
        prompt: String
    ): AppResult<String, DataError> {
        return when (ingredientsDetectionResult) {
            IngredientDetectionResult.SUCCESS_EMPTY -> AppResult.Success("")
            IngredientDetectionResult.SUCCESS_NON_EMPTY -> AppResult.Success("{ \"vegetables\": [\"carrot\"] }")
            IngredientDetectionResult.FAILURE -> AppResult.Error(DataError.UNKNOWN)
        }
    }

}

enum class IngredientDetectionResult {
    SUCCESS_EMPTY, SUCCESS_NON_EMPTY, FAILURE
}