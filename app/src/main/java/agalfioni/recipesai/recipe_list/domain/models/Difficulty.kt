package agalfioni.recipesai.recipe_list.domain.models

import agalfioni.recipesai.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Difficulty {
    @SerialName("easy")
    EASY,

    @SerialName("moderate")
    MODERATE,

    @SerialName("elaborated")
    ELABORATED
}

fun Difficulty.toDisplayString(): Int {
    return when (this) {
        Difficulty.EASY -> R.string.difficulty_easy
        Difficulty.MODERATE -> R.string.difficulty_medium
        Difficulty.ELABORATED -> R.string.difficulty_hard
    }
}