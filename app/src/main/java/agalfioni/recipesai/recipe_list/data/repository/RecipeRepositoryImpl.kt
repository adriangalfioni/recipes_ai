package agalfioni.recipesai.recipe_list.data.repository

import agalfioni.recipesai.recipe_list.data.local.RecipeDao
import agalfioni.recipesai.recipe_list.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlinx.coroutines.yield

class RecipeRepositoryImpl(
    private val recipeDao: RecipeDao,
): RecipeRepository {

    override suspend fun save(recipes: List<Recipe>) {
        recipes.forEach { recipe ->
            yield()
            recipeDao.saveFullRecipe(recipe)
        }
    }
}