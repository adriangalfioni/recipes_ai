package agalfioni.recipesai.recipe_list.domain.interfaces

import agalfioni.recipesai.recipe_list.domain.models.Recipe

interface RecipeRepository {
    suspend fun save(recipes: List<Recipe>)
}
