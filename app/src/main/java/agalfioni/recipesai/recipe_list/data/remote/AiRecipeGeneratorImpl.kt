package agalfioni.recipesai.recipe_list.data.remote

import agalfioni.recipesai.core.data.helpers.AiJsonParser
import agalfioni.recipesai.core.data.helpers.safeAiCall
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.map
import agalfioni.recipesai.recipe_list.data.utils.GenerateRecipesPromptProvider
import agalfioni.recipesai.recipe_list.domain.interfaces.AiRecipeGenerator
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.util.UUID

class AiRecipeGeneratorImpl(
    private val aiRecipeGeneratorDataSource: AiRecipeGeneratorDataSource,
    private val aiJsonParser: AiJsonParser
): AiRecipeGenerator {

    override suspend fun generateRecipes(
        ingredients: List<String>,
        recipesQty: Int,
    ): AppResult<List<Recipe>, DataError> {
        val rawJsonResult = safeAiCall {
            aiRecipeGeneratorDataSource.generateRecipes(
                prompt = GenerateRecipesPromptProvider.generateRecipePrompt(
                    ingredients = ingredients,
                    recipesQty = recipesQty
                )
            )
        }

        return withContext(Dispatchers.Default) {
            yield()
            rawJsonResult.map { rawJson ->
                val parsedRecipes = aiJsonParser.parseOrNull<List<Recipe>>(rawJson) ?: emptyList()

                parsedRecipes.map {
                    it.copy(id = UUID.randomUUID().toString())
                }
            }
        }
    }
}