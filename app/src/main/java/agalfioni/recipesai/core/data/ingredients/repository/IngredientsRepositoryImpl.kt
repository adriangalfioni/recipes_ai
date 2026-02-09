package agalfioni.recipesai.core.data.ingredients.repository

import agalfioni.recipesai.core.data.repository.LocalIngredientsDataSource
import agalfioni.recipesai.core.domain.models.LocalizedIngredient
import agalfioni.recipesai.core.ingredients.domain.interfaces.IngredientsRepository

class IngredientsRepositoryImpl(
    private val localIngredientsDataSource: LocalIngredientsDataSource,
) : IngredientsRepository {

    override suspend fun getIngredients(): Result<List<LocalizedIngredient>> {
        return Result.success(localIngredientsDataSource.loadIngredients())
    }

}