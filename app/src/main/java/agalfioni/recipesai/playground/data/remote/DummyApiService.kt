package agalfioni.recipesai.playground.data.remote

import agalfioni.recipesai.recipe.domain.models.Recipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyApiService {

    @GET("recipes/list")
    suspend fun getRecipes(
        @Query("query") query: String,
    ): Response<List<Recipe>>

}