package agalfioni.recipesai.recipe.data.repository

import agalfioni.recipesai.recipe.data.local.daos.RecipeDao
import agalfioni.recipesai.recipe.data.local.mappers.toDomain
import agalfioni.recipesai.recipe.domain.interfaces.RecipeRepository
import agalfioni.recipesai.recipe.domain.models.Recipe
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeRepositoryImpl(
    private val recipeDao: RecipeDao,
): RecipeRepository {

    override suspend fun save(recipes: List<Recipe>) {
        recipeDao.saveFullRecipes(recipes)
    }

    override fun getRecipes(): Flow<PagingData<Recipe>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { recipeDao.getRecipes() }
        )
            .flow
            .map { pagingData ->
                pagingData.map {
                    it.toDomain()
                }
            }
    }

    override fun getRecipeById(id: String): Flow<Recipe?> {
        return recipeDao.getRecipeById(id).map {
            it?.toDomain()
        }
    }
}