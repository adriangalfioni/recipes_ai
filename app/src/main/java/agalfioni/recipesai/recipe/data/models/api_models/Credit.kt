package agalfioni.recipesai.recipe.data.models.api_models

data class Credit(
    val id: Int,
    val image_url: String,
    val name: String,
    val slug: String,
    val type: String
)