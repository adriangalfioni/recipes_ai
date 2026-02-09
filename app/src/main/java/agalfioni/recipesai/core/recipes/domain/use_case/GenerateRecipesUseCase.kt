package agalfioni.recipesai.core.recipes.domain.use_case

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.onSuccess
import agalfioni.recipesai.core.recipes.domain.interfaces.RecipeRepository
import agalfioni.recipesai.core.recipes.domain.models.Recipe
import agalfioni.recipesai.core.ai.domain.generator.AiRecipeGenerator
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