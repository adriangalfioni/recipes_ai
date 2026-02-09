package agalfioni.recipesai.recipe.recipe_list.presentation.models

import androidx.annotation.StringRes

data class RecipeUi(
    val id: String,
    val title: String,
    @StringRes val difficulty: Int,
    val minutesTime: String,
    val ingredientCoveragePercentage: String,
    val isMatchHigh: Boolean,
    val totalCalories: String
)
