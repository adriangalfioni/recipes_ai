package agalfioni.recipesai.recipe_list.domain.usecase

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.onSuccess
import agalfioni.recipesai.recipe_list.domain.GenerationTracker
import agalfioni.recipesai.recipe_list.domain.RecipeGenerationEvent
import agalfioni.recipesai.recipe_list.domain.interfaces.AiRecipeGenerator
import agalfioni.recipesai.recipe_list.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlin.coroutines.cancellation.CancellationException

class GenerateRecipesUseCase(
    private val aiRecipeGenerator: AiRecipeGenerator,
    private val recipeRepository: RecipeRepository,
    private val generationTracker: GenerationTracker
) {

    suspend operator fun invoke(
        ingredients: List<String>,
        recipesQty: Int,
    ): AppResult<List<Recipe>, DataError> {
        generationTracker.update(RecipeGenerationEvent.Started)
        var completedNormally = false

        try {
            val recipesResult = aiRecipeGenerator.generateRecipes(
                ingredients = ingredients,
                recipesQty = recipesQty
            )

            recipesResult.onSuccess { recipes ->
                recipeRepository.save(recipes)
            }

            completedNormally = true
            return recipesResult
        } catch (e: CancellationException) {
            generationTracker.update(RecipeGenerationEvent.Error)
            throw e
        } finally {
            if (completedNormally) {
                generationTracker.update(RecipeGenerationEvent.Completed)
            }
        }

    }
}
