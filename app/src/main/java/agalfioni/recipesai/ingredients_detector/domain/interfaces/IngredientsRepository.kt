package agalfioni.recipesai.ingredients_detector.domain.interfaces

import agalfioni.recipesai.core.domain.models.LocalIngredient

interface IngredientsRepository {
    suspend fun getLocalIngredients(): Result<List<LocalIngredient>>
}