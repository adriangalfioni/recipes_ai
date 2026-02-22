package agalfioni.recipesai.recipe.data.repository

import agalfioni.recipesai.core.data.helpers.safeApiCall
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.recipe.data.models.api_models.RecipesResult
import agalfioni.recipesai.recipe.data.remote.TastyApiService
import agalfioni.recipesai.recipe.domain.interfaces.TastyRepository

class TastyRepositoryImpl(
    private val api: TastyApiService
): TastyRepository {

    override suspend fun getQuickRecipes(): AppResult<RecipesResult, DataError> {
        return safeApiCall {
            api.getRecipes(0, 4, "under_30_minutes")
        }
    }
}
