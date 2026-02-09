package agalfioni.recipesai.core.recipes.domain.interfaces

import agalfioni.recipesai.core.recipes.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun save(recipes: List<Recipe>)

    fun getAllRecipes(): Flow<List<Recipe>>

    fun getRecipeById(id: String): Flow<Recipe?>
}