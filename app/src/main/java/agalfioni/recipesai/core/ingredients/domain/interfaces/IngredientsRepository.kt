package agalfioni.recipesai.core.ingredients.domain.interfaces

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.domain.models.LocalizedIngredient

interface IngredientsRepository {
    suspend fun getIngredients(): Result<List<LocalizedIngredient>>

}