package agalfioni.recipesai.recipe.data.remote

import agalfioni.recipesai.recipe.data.models.api_models.RecipesResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TastyApiService {

    @GET("recipes/list")
    suspend fun getRecipes(
        @Query("from") from: Int,
        @Query("size") size: Int,
        @Query("tags") tags: String
    ): Response<RecipesResult>
}
