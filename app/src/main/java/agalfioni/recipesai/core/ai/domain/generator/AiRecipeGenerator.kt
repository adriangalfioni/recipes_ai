package agalfioni.recipesai.core.ai.domain.generator

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.recipes.domain.models.Recipe

interface AiRecipeGenerator {
    suspend fun generateRecipes(
        ingredients: List<String>,
        recipesQty: Int,
    ): AppResult<List<Recipe>, DataError>
}