package agalfioni.recipesai.recipe.presentation.recipelist.previewproviders

import agalfioni.recipesai.R
import agalfioni.recipesai.recipe.presentation.recipelist.models.RecipeUi
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

private val recipes =
    listOf(
        RecipeUi(
            id = "1",
            title = "Spaghetti Bolognese",
            difficulty = R.string.difficulty_easy,
            minutesTime = "20 min",
            ingredientCoveragePercentage = "80%",
            isMatchHigh = true,
            totalCalories = "500 kcal",
        ),
        RecipeUi(
            id = "2",
            title = "Beef Wellington",
            difficulty = R.string.difficulty_hard,
            minutesTime = "120 min",
            ingredientCoveragePercentage = "30%",
            isMatchHigh = false,
            totalCalories = "950 kcal",
        ),
    )

class RecipeUiProvider : PreviewParameterProvider<RecipeUi> {
    override val values = recipes.asSequence()
}

class RecipeUiListProvider : PreviewParameterProvider<List<RecipeUi>> {
    override val values = sequenceOf(recipes)
}
