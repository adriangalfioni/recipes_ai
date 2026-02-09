package agalfioni.recipesai.core.data.recipes.repository

import agalfioni.recipesai.core.data.recipes.local.RecipeDao
import agalfioni.recipesai.core.data.recipes.local.mappers.toDomain
import agalfioni.recipesai.core.recipes.domain.interfaces.RecipeRepository
import agalfioni.recipesai.core.recipes.domain.models.Recipe
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

    override fun getRecipeById(id: String): Flow<Recipe?> {
        return recipeDao.getRecipeById(id).map {
            it?.toDomain()
        }
    }
}