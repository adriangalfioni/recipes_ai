package agalfioni.recipesai.recipe.data.repository

import agalfioni.recipesai.recipe.data.models.SyncResult
import agalfioni.recipesai.recipe.domain.interfaces.RecipesSyncRepository
import agalfioni.recipesai.recipe.domain.models.Recipe

class FakeRecipesSyncRepository: RecipesSyncRepository {

    private val _cachedRecipes = mutableListOf<Recipe>()

    fun getCachedRecipes(): List<Recipe> = _cachedRecipes.toList()

    var recipesSyncResultType: RecipesSyncResultType = RecipesSyncResultType.SUCCESS

    override suspend fun save(recipes: List<Recipe>) {
        _cachedRecipes.addAll(recipes)
    }

    override suspend fun syncRecipes(): SyncResult {
        return when (recipesSyncResultType) {
            RecipesSyncResultType.SUCCESS -> SyncResult.Success(_cachedRecipes.size)
            RecipesSyncResultType.PARTIAL_SUCCESS -> SyncResult.PartialSuccess(_cachedRecipes.size, _cachedRecipes.size)
            RecipesSyncResultType.ERROR -> SyncResult.Error("Error syncing recipes")
        }
    }

}

enum class RecipesSyncResultType {
    SUCCESS,
    PARTIAL_SUCCESS,
    ERROR
}