package agalfioni.recipesai.ingredients_detector.data.parser

import agalfioni.recipesai.ingredients_detector.domain.models.IngredientsResult
import agalfioni.recipesai.ingredients_detector.domain.interfaces.IngredientsParser
import android.util.Log
import kotlinx.serialization.json.Json

class JsonIngredientParser: IngredientsParser {
    private val jsonParser = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    override fun parseIngredients(rawJson: String): IngredientsResult {
        return try {
            // We parse the string into the Domain object directly
            // if the JSON keys match our property names.
            jsonParser.decodeFromString<IngredientsResult>(rawJson)
        } catch (e: Exception) {
            // Fallback or error handling if AI returns malformed JSON
            Log.e("AI_PARSE", "Failed to parse: $rawJson", e)
            IngredientsResult()
        }
    }
}