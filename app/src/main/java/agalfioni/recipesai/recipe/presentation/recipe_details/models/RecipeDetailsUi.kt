package agalfioni.recipesai.recipe.presentation.recipe_details.models

data class RecipeDetailsUi(
    val id: String,
    val title: String,
    val minutesTime: String,
    val category: String,
    val totalCalories: String,
    val aiScore: Int,
    val chefInsight: String?,
    val ingredients: List<Ingredient>,
    val instructions: List<Instruction>
)

data class Ingredient(val name: String, val detail: String)

data class Instruction(
    val step: Int,
    val title: String,
    val description: String,
    val positionInList: PositionInList = PositionInList.MIDDLE
)

enum class PositionInList {
    START,
    MIDDLE,
    END
}