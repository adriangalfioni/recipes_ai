package agalfioni.recipesai.recipe.recipe_details.presentation.models

import agalfioni.recipesai.core.domain.models.AppError

enum class RecipeDetailsError: AppError {
    RECIPE_NOT_FOUND
}