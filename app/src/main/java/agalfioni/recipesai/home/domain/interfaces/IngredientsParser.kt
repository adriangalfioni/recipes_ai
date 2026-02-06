package agalfioni.recipesai.home.domain.interfaces

import agalfioni.recipesai.home.domain.IngredientsResult

interface IngredientsParser {
    fun parseIngredients(rawJson: String): IngredientsResult
}