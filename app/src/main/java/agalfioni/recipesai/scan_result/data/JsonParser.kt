package agalfioni.recipesai.scan_result.data

import agalfioni.recipesai.scan_result.domain.IngredientsResult
import android.util.Log
import kotlinx.serialization.json.Json

private val jsonParser = Json {
    ignoreUnknownKeys = true 
    coerceInputValues = true 
}

fun parseIngredients(rawJson: String): IngredientsResult {
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