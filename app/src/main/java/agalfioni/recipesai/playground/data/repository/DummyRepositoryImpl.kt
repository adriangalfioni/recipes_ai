package agalfioni.recipesai.playground.data.repository

import agalfioni.recipesai.playground.data.remote.DummyApiService
import agalfioni.recipesai.playground.domain.DummyRepository
import agalfioni.recipesai.recipe.domain.models.Recipe

class DummyRepositoryImpl(
    private val api: DummyApiService
): DummyRepository {

    override suspend fun queryRecipes(query: String): Result<List<Recipe>> {
        return try {
            val result = api.getRecipes(query)
            if (result.isSuccessful) {
                val listRecipes = result.body()
                if (listRecipes != null) {
                    // Map to domain
                    Result.success(listRecipes)
                } else {
                    Result.failure(Exception("Exception"))
                }
            } else {
                // here user error code
                val errorCode = result.code()
                Result.failure(Exception("Exception $errorCode"))
            }

        } catch ( _: Exception) {
            Result.failure(Exception("Server error"))
        }
    }
}