package agalfioni.recipesai.recipe_details.data

import agalfioni.recipesai.recipe_details.domain.RecipeDetailsRepository
import agalfioni.recipesai.recipe_list.data.local.RecipeDao
import agalfioni.recipesai.recipe_list.data.local.mappers.toDomain
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeDetailsRepositoryImpl(
    private val recipeDao: RecipeDao
): RecipeDetailsRepository {

    override fun getRecipeById(id: String): Flow<Recipe?> {
        return recipeDao.getRecipeById(id).map {
            it?.toDomain()
        }
    }
}