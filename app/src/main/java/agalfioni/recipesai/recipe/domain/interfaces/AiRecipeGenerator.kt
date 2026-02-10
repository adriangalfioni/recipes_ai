package agalfioni.recipesai.recipe.domain.interfaces

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.recipe.domain.models.Recipe

interface AiRecipeGenerator {
    suspend fun generateRecipes(
        ingredients: List<String>,
        recipesQty: Int,
    ): AppResult<List<Recipe>, DataError>
}
