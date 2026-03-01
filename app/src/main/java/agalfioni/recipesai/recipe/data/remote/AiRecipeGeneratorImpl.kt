package agalfioni.recipesai.recipe.data.remote

import agalfioni.recipesai.core.data.helpers.AiJsonParser
import agalfioni.recipesai.core.data.helpers.safeAiCall
import agalfioni.recipesai.core.domain.interfaces.LanguageProvider
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.map
import agalfioni.recipesai.recipe.domain.interfaces.AiRecipeGenerator
import agalfioni.recipesai.recipe.domain.models.Recipe
import agalfioni.recipesai.recipe.domain.providers.GenerateRecipesPromptProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class AiRecipeGeneratorImpl(
    private val aiRecipeGeneratorDataSource: AiRecipeGeneratorDataSource,
    private val aiJsonParser: AiJsonParser,
    private val languageProvider: LanguageProvider,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : AiRecipeGenerator {
    override suspend fun generateRecipes(
        ingredients: List<String>,
        recipesQty: Int,
    ): AppResult<List<Recipe>, DataError> {
        val rawJsonResult =
            safeAiCall {
                aiRecipeGeneratorDataSource.generateRecipes(
                    prompt =
                        GenerateRecipesPromptProvider.generateRecipePrompt(
                            language = languageProvider.getLanguage(),
                            ingredients = ingredients,
                            recipesQty = recipesQty,
                        ),
                )
            }

        return withContext(dispatcher) {
            rawJsonResult.map { rawJson ->
                val parsedRecipes = aiJsonParser.parseOrNull<List<Recipe>>(rawJson) ?: emptyList()

                parsedRecipes.map {
                    it.copy(id = UUID.randomUUID().toString())
                }
            }
        }
    }
}
