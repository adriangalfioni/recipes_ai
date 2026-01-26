package agalfioni.recipesai.recipe_list.domain.models

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
