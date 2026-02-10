package agalfioni.recipesai.recipe.domain.interfaces

import agalfioni.recipesai.recipe.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun save(recipes: List<Recipe>)

    fun getAllRecipes(): Flow<List<Recipe>>

    fun getRecipeById(id: String): Flow<Recipe?>
}
