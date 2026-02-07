package agalfioni.recipesai.home.data.parser

import agalfioni.recipesai.home.domain.IngredientsResult
import agalfioni.recipesai.home.domain.interfaces.IngredientsParser

class FakeIngredientParser: IngredientsParser {

    override fun parseIngredients(rawJson: String): IngredientsResult {
        return IngredientsResult(
            vegetables = listOf("carrot")
        )
    }

}