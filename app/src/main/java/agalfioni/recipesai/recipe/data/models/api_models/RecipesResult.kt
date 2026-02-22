package agalfioni.recipesai.recipe.data.models.api_models

data class RecipesResult(
    val count: Int,
    val results: List<Result>
)