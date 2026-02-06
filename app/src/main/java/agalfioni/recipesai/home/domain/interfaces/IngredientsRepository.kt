package agalfioni.recipesai.home.domain.interfaces

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.LocalIngredient

interface IngredientsRepository {
    suspend fun getLocalIngredients(): Result<List<LocalIngredient>>
    suspend fun detectIngredientsAI(bytearray: ByteArray, prompt: String): AppResult<String, DataError>
}