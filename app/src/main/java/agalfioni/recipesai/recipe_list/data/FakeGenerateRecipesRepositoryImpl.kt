package agalfioni.recipesai.recipe_list.data

import agalfioni.recipesai.core.data.helpers.AiJsonParser
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.recipe_list.data.utils.fakeAIJsonRawResponse
import agalfioni.recipesai.recipe_list.domain.GenerateRecipesRepository
import agalfioni.recipesai.recipe_list.domain.RecipeGenerationEvent
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


/* Fake repository in order to avoid wasting IA tokens */
class FakeGenerateRecipesRepositoryImpl(
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

        delay(1500)

        _generationEvents.emit(RecipeGenerationEvent.Completed)

        val recipes = aiJsonParser.parseOrNull<List<Recipe>>(fakeAIJsonRawResponse) ?: emptyList()
        return AppResult.Success(recipes)
    }

}