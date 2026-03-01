package agalfioni.recipesai.recipe.domain.usecase

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.onSuccess
import agalfioni.recipesai.recipe.domain.GenerationTracker
import agalfioni.recipesai.recipe.domain.RecipeGenerationEvent
import agalfioni.recipesai.recipe.domain.interfaces.AiRecipeGenerator
import agalfioni.recipesai.recipe.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe.domain.interfaces.RecipesSyncRepository
import agalfioni.recipesai.recipe.domain.models.Recipe
import kotlin.coroutines.cancellation.CancellationException

class GenerateRecipesUseCase(
    private val aiRecipeGenerator: AiRecipeGenerator,
    private val recipeRepository: RecipeRepository,
    private val recipesSyncRepository: RecipesSyncRepository,
    private val generationTracker: GenerationTracker,
) {
    suspend operator fun invoke(
        ingredients: List<String>,
        recipesQty: Int,
    ): AppResult<List<Recipe>, DataError> {
        generationTracker.update(RecipeGenerationEvent.Started)
        var completedNormally = false

        try {
            val recipesResult =
                aiRecipeGenerator.generateRecipes(
                    ingredients = ingredients,
                    recipesQty = recipesQty,
                )

            recipesResult.onSuccess { recipes ->
                recipeRepository.save(recipes)
                recipesSyncRepository.save(recipes)
            }

            completedNormally = true
            return recipesResult
        } catch (e: CancellationException) {
            generationTracker.update(RecipeGenerationEvent.Error)
            throw e
        } catch (_: Exception) {
            return AppResult.Error(DataError.UNKNOWN)
        } finally {
            if (completedNormally) {
                generationTracker.update(RecipeGenerationEvent.Completed)
            }
        }
    }
}
