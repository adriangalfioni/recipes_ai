package agalfioni.recipesai.ingredients_detector.domain.interfaces

import agalfioni.recipesai.ingredients_detector.domain.models.IngredientsResult

interface IngredientsParser {
    fun parseIngredients(rawJson: String): IngredientsResult
}