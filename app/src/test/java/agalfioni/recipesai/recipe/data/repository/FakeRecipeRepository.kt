package agalfioni.recipesai.recipe.data.repository

import agalfioni.recipesai.recipe.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe.domain.models.Recipe
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRecipeRepository : RecipeRepository {
    private val _recipes = mutableListOf<Recipe>()

    fun getCachedRecipes(): List<Recipe> = _recipes.toList()

    override suspend fun save(recipes: List<Recipe>) {
        _recipes.addAll(recipes)
    }

    override fun getRecipes(): Flow<PagingData<Recipe>> = flowOf(_recipes)

    override fun getRecipeById(id: String): Flow<Recipe?> = flowOf(_recipes.find { it.id == id })
}
