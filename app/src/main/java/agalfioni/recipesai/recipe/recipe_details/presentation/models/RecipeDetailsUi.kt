package agalfioni.recipesai.recipe.recipe_details.presentation.models

data class RecipeDetailsUi(
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