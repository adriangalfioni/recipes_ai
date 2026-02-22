package agalfioni.recipesai.recipe.data.models.api_models

data class Section(
    val components: List<Component>,
    val name: String,
    val position: Int
)