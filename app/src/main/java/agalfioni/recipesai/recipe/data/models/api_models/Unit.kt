package agalfioni.recipesai.recipe.data.models.api_models

data class Unit(
    val abbreviation: String,
    val display_plural: String,
    val display_singular: String,
    val name: String,
    val system: String
)