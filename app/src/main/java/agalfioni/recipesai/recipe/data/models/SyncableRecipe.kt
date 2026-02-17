package agalfioni.recipesai.recipe.data.models

import agalfioni.recipesai.recipe.domain.models.Difficulty
import agalfioni.recipesai.recipe.domain.models.Nutrition
import agalfioni.recipesai.recipe.domain.models.RecipeIngredient
import agalfioni.recipesai.recipe.domain.models.RecipeInstruction
import kotlinx.serialization.SerialName

const val DEFAULT_USER_ID = "guest"

data class SyncableRecipe(
    val id: String,
    val ownerId: String = DEFAULT_USER_ID,
    val title: String,
    val difficulty: Difficulty,
    @SerialName("minutes_time")
    val minutesTime: Int,
    @SerialName("ingredient_coverage")
    val ingredientCoverage: Double,
    val instructions: List<RecipeInstruction>,
    val nutrition: Nutrition,
    val ingredients: List<RecipeIngredient>,
    val ingredientNames: List<String>
)
