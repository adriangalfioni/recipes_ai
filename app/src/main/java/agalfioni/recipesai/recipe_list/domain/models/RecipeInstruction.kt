package agalfioni.recipesai.recipe_list.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class RecipeInstruction(
    val title: String,
    val description: String
)
