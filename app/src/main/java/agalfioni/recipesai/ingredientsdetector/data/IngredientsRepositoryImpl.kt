package agalfioni.recipesai.ingredientsdetector.data

import agalfioni.recipesai.core.domain.models.LocalIngredient
import agalfioni.recipesai.ingredientsdetector.data.datasource.LocalizedIngredientsDataSource
import agalfioni.recipesai.ingredientsdetector.domain.interfaces.IngredientsRepository

class IngredientsRepositoryImpl(
    private val localizedIngredientsDataSource: LocalizedIngredientsDataSource,
) : IngredientsRepository {
    override suspend fun getLocalIngredients(): Result<List<LocalIngredient>> =
        Result.success(localizedIngredientsDataSource.loadIngredients())
}
