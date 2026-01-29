package agalfioni.recipesai.recipe_list.data.repository

import agalfioni.recipesai.core.data.helpers.AiJsonParser
import agalfioni.recipesai.core.data.helpers.safeAiCall
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.map
import agalfioni.recipesai.core.domain.models.onSuccess
import agalfioni.recipesai.recipe_list.data.local.RecipeDao
import agalfioni.recipesai.recipe_list.data.remote.AiRecipeGeneratorDataSource
import agalfioni.recipesai.recipe_list.data.utils.GenerateRecipesPromptProvider
import agalfioni.recipesai.recipe_list.domain.GenerateRecipesRepository
import agalfioni.recipesai.recipe_list.domain.RecipeGenerationEvent
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.UUID

class GenerateRecipesRepositoryImpl(
    private val aiRecipeGeneratorDataSource: AiRecipeGeneratorDataSource,
    private val recipeDao: RecipeDao,
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
            aiRecipeGeneratorDataSource.generateRecipes(
                prompt = GenerateRecipesPromptProvider.generateRecipePrompt(
                    ingredients = ingredients,
                    recipesQty = recipesQty
                )
            )
        }

        val result =  rawJsonResult.map { rawJson ->
            (aiJsonParser.parseOrNull<List<Recipe>>(rawJson) ?: emptyList()).map {
                it.copy(id = UUID.randomUUID().toString())
            }
        }

        result.onSuccess { recipes ->
            saveRecipesToDb(recipes)
        }
        _generationEvents.emit(RecipeGenerationEvent.Completed)

        return result
    }

    private suspend fun saveRecipesToDb(recipes: List<Recipe>) {
        recipes.forEach { recipe ->
            recipeDao.saveFullRecipe(recipe)
        }
    }

}