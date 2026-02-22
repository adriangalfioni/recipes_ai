package agalfioni.recipesai.recipe.data.models.api_models

data class UserRatings(
    val count_negative: Int,
    val count_positive: Int,
    val score: Double
)