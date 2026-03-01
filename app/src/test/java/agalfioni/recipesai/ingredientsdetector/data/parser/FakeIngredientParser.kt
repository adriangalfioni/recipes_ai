package agalfioni.recipesai.ingredientsdetector.data.parser

import agalfioni.recipesai.ingredientsdetector.domain.interfaces.IngredientsParser
import agalfioni.recipesai.ingredientsdetector.domain.models.IngredientsResult

class FakeIngredientParser : IngredientsParser {
    override fun parseIngredients(rawJson: String): IngredientsResult =
        IngredientsResult(
            vegetables = listOf("carrot"),
        )
}
