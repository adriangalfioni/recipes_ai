package agalfioni.recipesai.recipe.data.repository

import agalfioni.recipesai.recipe.data.local.daos.RecipesSyncDao
import agalfioni.recipesai.recipe.data.local.entity.RecipeSyncEntity
import agalfioni.recipesai.recipe.domain.interfaces.RecipesSyncRepository
import agalfioni.recipesai.recipe.domain.models.Recipe

class RecipesSyncRepositoryImpl(
    private val recipeSyncDao: RecipesSyncDao
): RecipesSyncRepository {

    override suspend fun save(recipes: List<Recipe>) {
        recipeSyncDao.upsertRecipesSync(
            recipes.map {
                RecipeSyncEntity(
                    recipeId = it.id
                )
            }
        )
    }
}