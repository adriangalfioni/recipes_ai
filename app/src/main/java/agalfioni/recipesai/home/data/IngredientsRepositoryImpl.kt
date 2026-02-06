package agalfioni.recipesai.home.data

import agalfioni.recipesai.core.data.helpers.safeAiCall
import agalfioni.recipesai.core.data.repository.LocalIngredientsLoader
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.LocalIngredient
import agalfioni.recipesai.home.domain.interfaces.IngredientsRepository

class IngredientsRepositoryImpl(
    private val aiRemoteDataSource: IngredientsDetectorDataSource,
    private val localIngredientsLoader: LocalIngredientsLoader,
) : IngredientsRepository {

    override suspend fun getLocalIngredients(): Result<List<LocalIngredient>> {
        return Result.success(localIngredientsLoader.loadIngredients())
    }

    override suspend fun detectIngredientsAI(bytearray: ByteArray, prompt: String): AppResult<String, DataError> {
        return safeAiCall {
            // Call AI with specific prompt
            aiRemoteDataSource.generateContent(
                compressedBytes = bytearray,
                prompt = prompt
            )
        }
    }
}