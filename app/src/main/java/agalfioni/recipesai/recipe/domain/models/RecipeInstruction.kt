package agalfioni.recipesai.recipe.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class RecipeInstruction(
    val title: String,
    val description: String,
)
