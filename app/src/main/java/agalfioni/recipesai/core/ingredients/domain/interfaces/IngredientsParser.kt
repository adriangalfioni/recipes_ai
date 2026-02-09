package agalfioni.recipesai.core.ingredients.domain.interfaces

import agalfioni.recipesai.core.scan.domain.model.IngredientsResult

interface IngredientsParser {
    fun parseIngredients(rawJson: String): IngredientsResult
}