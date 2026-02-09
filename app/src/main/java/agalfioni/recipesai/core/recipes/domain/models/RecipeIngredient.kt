package agalfioni.recipesai.core.recipes.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class RecipeIngredient(
    val name: String,
    val quantity: Double,
    val unit: String
)
