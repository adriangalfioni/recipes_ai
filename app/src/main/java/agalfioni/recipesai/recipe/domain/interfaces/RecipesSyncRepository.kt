package agalfioni.recipesai.recipe.domain.interfaces

import agalfioni.recipesai.recipe.domain.models.Recipe

interface RecipesSyncRepository {
    suspend fun save(recipes: List<Recipe>)

    suspend fun syncRecipes()
}
