package agalfioni.recipesai.recipe_list.domain.usecase

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.onSuccess
import agalfioni.recipesai.recipe_list.domain.GenerationTracker
import agalfioni.recipesai.recipe_list.domain.RecipeGenerationEvent
import agalfioni.recipesai.recipe_list.domain.interfaces.AiRecipeGenerator
import agalfioni.recipesai.recipe_list.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive

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

        try {
            val recipesResult = aiRecipeGenerator.generateRecipes(
                ingredients = ingredients,
                recipesQty = recipesQty
            )

            recipesResult.onSuccess { recipes ->
                recipeRepository.save(recipes)
            }

            return recipesResult
        } finally {
            // This runs even if a TimeoutCancellationException occurs!
            // You might want to check if the coroutine was cancelled to emit an Error event
            if (currentCoroutineContext().isActive.not()) {
                generationTracker.update(RecipeGenerationEvent.Error)
            } else {
                generationTracker.update(RecipeGenerationEvent.Completed)
            }
        }

    }
}
