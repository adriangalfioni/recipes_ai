package agalfioni.recipesai.recipe.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: String = "",
    val title: String,
    val difficulty: Difficulty,
    @SerialName("minutes_time")
    val minutesTime: Int,
    @SerialName("ingredient_coverage")
    val ingredientCoverage: Double,
    val instructions: List<RecipeInstruction>,
    val nutrition: Nutrition,
    val ingredients: List<RecipeIngredient>
)
