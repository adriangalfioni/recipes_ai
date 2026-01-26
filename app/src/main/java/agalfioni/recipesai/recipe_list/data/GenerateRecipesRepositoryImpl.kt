package agalfioni.recipesai.recipe_list.data

import agalfioni.recipesai.core.data.helpers.AiJsonParser
import agalfioni.recipesai.core.data.helpers.safeAiCall
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.map
import agalfioni.recipesai.recipe_list.data.utils.GenerateRecipesPromptProvider
import agalfioni.recipesai.recipe_list.domain.GenerateRecipesRepository
import agalfioni.recipesai.recipe_list.domain.RecipeGenerationEvent
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class GenerateRecipesRepositoryImpl(
    private val recipeGeneratorDataSource: RecipeGeneratorDataSource,
    private val aiJsonParser: AiJsonParser
) : GenerateRecipesRepository {

    private val _generationEvents =
        MutableSharedFlow<RecipeGenerationEvent>(
            replay = 1,
            extraBufferCapacity = 1
        )

    override val generationEvents: Flow<RecipeGenerationEvent> =
        _generationEvents.asSharedFlow()

    override suspend fun generateRecipes(
        recipesQty: Int,
        ingredients: List<String>
    ): AppResult<List<Recipe>, DataError> {
        _generationEvents.emit(RecipeGenerationEvent.Started)

        val rawJsonResult = safeAiCall {
            recipeGeneratorDataSource.generateRecipes(
                prompt = GenerateRecipesPromptProvider.generateRecipePrompt(
                    ingredients = ingredients,
                    recipesQty = recipesQty
                )
            )
        }

        _generationEvents.emit(RecipeGenerationEvent.Completed)

        return rawJsonResult.map { rawJson ->
            aiJsonParser.parseOrNull<List<Recipe>>(rawJson) ?: emptyList()
        }
    }

}