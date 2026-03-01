package agalfioni.recipesai.ingredientsdetector.data

import agalfioni.recipesai.core.domain.models.LocalIngredient
import agalfioni.recipesai.ingredientsdetector.domain.interfaces.IngredientsRepository

class FakeIngredientsRepository : IngredientsRepository {
    override suspend fun getLocalIngredients(): Result<List<LocalIngredient>> = Result.success(emptyList())
}
