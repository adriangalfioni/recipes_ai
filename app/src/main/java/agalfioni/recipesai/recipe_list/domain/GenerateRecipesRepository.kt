package agalfioni.recipesai.recipe_list.domain

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface GenerateRecipesRepository {

    val generationEvents: Flow<RecipeGenerationEvent>

    suspend fun generateRecipes(recipesQty: Int, ingredients: List<String>): AppResult<List<Recipe>, DataError>
}