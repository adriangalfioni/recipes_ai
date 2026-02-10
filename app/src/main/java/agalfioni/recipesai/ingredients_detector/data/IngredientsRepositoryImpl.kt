package agalfioni.recipesai.ingredients_detector.data

import agalfioni.recipesai.ingredients_detector.data.data_source.LocalizedIngredientsDataSource
import agalfioni.recipesai.core.domain.models.LocalIngredient
import agalfioni.recipesai.ingredients_detector.domain.interfaces.IngredientsRepository

class IngredientsRepositoryImpl(
    private val localizedIngredientsDataSource: LocalizedIngredientsDataSource,
) : IngredientsRepository {

    override suspend fun getLocalIngredients(): Result<List<LocalIngredient>> {
        return Result.success(localizedIngredientsDataSource.loadIngredients())
    }
}