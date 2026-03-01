package agalfioni.recipesai.recipe.domain.usecase

import agalfioni.recipesai.recipe.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe.domain.interfaces.RecipesSyncRepository

class SyncRecipesUseCase(
    private val recipesSyncRepository: RecipesSyncRepository,
    private val recipeRepository: RecipeRepository,
) {
    suspend operator fun invoke() {
        // 1. obtain recipes not synced
        // 2. upload to firebase
        // 3. mark synced recipes
    }
}
