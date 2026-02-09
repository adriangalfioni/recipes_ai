package agalfioni.recipesai.recipe_list.domain.interfaces

import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun save(recipes: List<Recipe>)

    fun getAllRecipes(): Flow<List<Recipe>>
}
