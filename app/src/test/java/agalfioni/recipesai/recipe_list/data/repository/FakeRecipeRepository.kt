package agalfioni.recipesai.recipe_list.data.repository

import agalfioni.recipesai.recipe_list.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class FakeRecipeRepository : RecipeRepository {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())

    fun getCachedRecipes(): List<Recipe> = _recipes.value

    override suspend fun save(recipes: List<Recipe>) {
        _recipes.value = recipes
    }

    override fun getAllRecipes(): Flow<List<Recipe>> {
        return flowOf(_recipes.value)
    }

}