package agalfioni.recipesai.playground.domain

import agalfioni.recipesai.recipe.domain.models.Recipe

interface DummyRepository {

    suspend fun queryRecipes(query: String): Result<List<Recipe>>

}