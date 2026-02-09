package agalfioni.recipesai.home.data

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.LocalizedIngredient
import agalfioni.recipesai.core.ingredients.domain.interfaces.IngredientsRepository

class FakeIngredientsRepository: IngredientsRepository {

    var ingredientsDetectionResult = IngredientDetectionResult.SUCCESS_NON_EMPTY

    override suspend fun getIngredients(): Result<List<LocalizedIngredient>> {
        return Result.success(emptyList())
    }

    override suspend fun detectIngredientsAI(
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