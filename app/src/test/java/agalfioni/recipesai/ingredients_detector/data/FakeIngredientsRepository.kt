package agalfioni.recipesai.ingredients_detector.data

import agalfioni.recipesai.core.domain.models.LocalIngredient
import agalfioni.recipesai.ingredients_detector.domain.interfaces.IngredientsRepository

class FakeIngredientsRepository: IngredientsRepository {

    override suspend fun getLocalIngredients(): Result<List<LocalIngredient>> {
        return Result.success(emptyList())
    }

}
