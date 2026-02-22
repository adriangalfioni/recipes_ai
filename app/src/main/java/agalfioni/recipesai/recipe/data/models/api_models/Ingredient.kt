package agalfioni.recipesai.recipe.data.models.api_models

data class Ingredient(
    val created_at: Int,
    val display_plural: String,
    val display_singular: String,
    val id: Int,
    val name: String,
    val updated_at: Int
)