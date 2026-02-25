package agalfioni.recipesai.recipe.domain.interfaces

import agalfioni.recipesai.recipe.domain.models.Recipe
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun save(recipes: List<Recipe>)

    fun getRecipes(): Flow<PagingData<Recipe>>

    fun getRecipeById(id: String): Flow<Recipe?>
}
