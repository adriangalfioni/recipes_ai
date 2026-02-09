package agalfioni.recipesai.recipe_list.data.repository

import agalfioni.recipesai.recipe_list.data.local.RecipeDao
import agalfioni.recipesai.recipe_list.data.local.mappers.toDomain
import agalfioni.recipesai.recipe_list.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getAllRecipes(): Flow<List<Recipe>> {
        return recipeDao.getAllRecipes().map {
            it.toDomain()
        }
    }
}