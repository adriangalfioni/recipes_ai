package agalfioni.recipesai.ingredientsdetector.data.parser

import agalfioni.recipesai.ingredientsdetector.domain.interfaces.IngredientsParser
import agalfioni.recipesai.ingredientsdetector.domain.models.IngredientsResult
import android.util.Log
import kotlinx.serialization.json.Json

class JsonIngredientParser : IngredientsParser {
    private val jsonParser =
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

    override fun parseIngredients(rawJson: String): IngredientsResult =
        try {
            // We parse the string into the Domain object directly
            // if the JSON keys match our property names.
            jsonParser.decodeFromString<IngredientsResult>(rawJson)
        } catch (e: Exception) {
            // Fallback or error handling if AI returns malformed JSON
            Log.e("AI_PARSE", "Failed to parse: $rawJson", e)
            IngredientsResult()
        }
}
