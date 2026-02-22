package agalfioni.recipesai.recipe.domain.interfaces

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.recipe.data.models.api_models.RecipesResult
import agalfioni.recipesai.recipe.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface TastyRepository {

    suspend fun getQuickRecipes(): AppResult<RecipesResult, DataError>

}
