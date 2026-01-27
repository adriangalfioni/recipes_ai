package agalfioni.recipesai.recipe_list.presentation.models

import androidx.annotation.StringRes

data class RecipeUi(
    val title: String,
    @StringRes val difficulty: Int,
    val minutesTime: String,
    val ingredientCoveragePercentage: String,
    val isMatchHigh: Boolean,
    val totalCalories: String
)
