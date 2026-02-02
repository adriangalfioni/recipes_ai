package agalfioni.recipesai.recipe_list.data.remote

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.recipe_list.domain.interfaces.AiRecipeGenerator
import agalfioni.recipesai.recipe_list.domain.models.Difficulty
import agalfioni.recipesai.recipe_list.domain.models.Nutrition
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlinx.coroutines.delay


class FakeAiRecipeGenerator(): AiRecipeGenerator {

    var delayMillis: Long = 0
    var aiRecipeGeneratorResultType: AiRecipeGeneratorResultType = AiRecipeGeneratorResultType.SUCCESS_NON_EMPTY

    var successResponse: AppResult<List<Recipe>, DataError> = AppResult.Success(
        listOf(
            Recipe(
                id = "1",
                title = "Spaghetti Bolognese",
                difficulty = Difficulty.EASY,
                minutesTime = 20,
                ingredientCoverage = 0.8,
                instructions = listOf(),
                nutrition = Nutrition(calories = 200.0),
                ingredients = listOf()
            ),
            Recipe(
                id = "2",
                title = "Roasted Chicken",
                difficulty = Difficulty.MODERATE,
                minutesTime = 50,
                ingredientCoverage = 1.0,
                instructions = listOf(),
                nutrition = Nutrition(calories = 450.0),
                ingredients = listOf()
            )
        )
    )

    private val successEmptyResponse: AppResult<List<Recipe>, DataError> = AppResult.Success(
        listOf()
    )

    override suspend fun generateRecipes(
        ingredients: List<String>,
        recipesQty: Int
    ): AppResult<List<Recipe>, DataError> {
        if (delayMillis > 0) delay(delayMillis)
        return when (aiRecipeGeneratorResultType) {
            AiRecipeGeneratorResultType.SUCCESS_EMPTY -> successEmptyResponse
            AiRecipeGeneratorResultType.SUCCESS_NON_EMPTY -> successResponse
            AiRecipeGeneratorResultType.FAILURE_TIMEOUT_EXCEPTION -> AppResult.Error(DataError.DEADLINE_EXCEEDED)
            AiRecipeGeneratorResultType.FAILURE_OTHER_EXCEPTION -> AppResult.Error(DataError.UNKNOWN)
            AiRecipeGeneratorResultType.COROUTINE_CANCELLATION_EXCEPTION -> throw kotlin.coroutines.cancellation.CancellationException()
        }
    }
}

enum class AiRecipeGeneratorResultType{
    SUCCESS_EMPTY,
    SUCCESS_NON_EMPTY,
    FAILURE_TIMEOUT_EXCEPTION,
    FAILURE_OTHER_EXCEPTION,
    COROUTINE_CANCELLATION_EXCEPTION,
}

