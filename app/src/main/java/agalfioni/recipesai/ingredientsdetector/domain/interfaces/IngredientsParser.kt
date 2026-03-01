package agalfioni.recipesai.ingredientsdetector.domain.interfaces

import agalfioni.recipesai.ingredientsdetector.domain.models.IngredientsResult

interface IngredientsParser {
    fun parseIngredients(rawJson: String): IngredientsResult
}
