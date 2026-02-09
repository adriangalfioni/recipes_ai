package agalfioni.recipesai.home.data.parser

import agalfioni.recipesai.core.scan.domain.model.IngredientsResult
import agalfioni.recipesai.core.ingredients.domain.interfaces.IngredientsParser

class FakeIngredientParser: IngredientsParser {

    override fun parseIngredients(rawJson: String): IngredientsResult {
        return IngredientsResult(
            vegetables = listOf("carrot")
        )
    }

}