package agalfioni.recipesai.recipe_list.presentation.preview_providers

import agalfioni.recipesai.R
import agalfioni.recipesai.recipe_list.presentation.models.RecipeUi
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class RecipeUiProvider : PreviewParameterProvider<RecipeUi> {
    override val values = sequenceOf(
        RecipeUi(
            title = "Spaghetti Bolognese",
            difficulty = R.string.difficulty_easy,
            minutesTime = "20 min",
            ingredientCoveragePercentage = "80%",
            isMatchHigh = true,
            totalCalories = "500 kcal"
        ),
        RecipeUi(
            title = "Beef Wellington",
            difficulty = R.string.difficulty_hard,
            minutesTime = "120 min",
            ingredientCoveragePercentage = "30%",
            isMatchHigh = false,
            totalCalories = "950 kcal"
        )
    )
}