package agalfioni.recipesai.ingredients_detector.data.parser

import agalfioni.recipesai.ingredients_detector.domain.models.IngredientsResult
import agalfioni.recipesai.ingredients_detector.domain.interfaces.IngredientsParser

class FakeIngredientParser: IngredientsParser {

    override fun parseIngredients(rawJson: String): IngredientsResult {
        return IngredientsResult(
            vegetables = listOf("carrot")
        )
    }

}