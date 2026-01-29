package agalfioni.recipesai.core.data.helpers

import kotlinx.serialization.json.Json

class AiJsonParser(
    val json: Json
) {

    inline fun <reified T> parseOrNull(input: String): T? =
        runCatching {
            json.decodeFromString<T>(input.extractJsonArray())
        }.getOrElse { t ->
            t.printStackTrace()
            null
        }

    inline fun <reified T> parseResult(input: String): Result<T> =
        runCatching {
            json.decodeFromString<T>(input.extractJsonArray())
        }
}

fun String.extractJsonArray(): String {
    val tempValue = trim()
        .removePrefix("```json")
        .removePrefix("```")
        .removeSuffix("```")
        .trim()

    with (tempValue) {
        val start = indexOf('[')
        val end = lastIndexOf(']')
        if (start == -1 || end == -1 || end <= start) return this
        return substring(start, end + 1)
    }
}
