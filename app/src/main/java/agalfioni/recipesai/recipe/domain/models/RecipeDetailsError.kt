package agalfioni.recipesai.recipe.domain.models

import agalfioni.recipesai.core.domain.models.AppError

enum class RecipeDetailsError : AppError {
    RECIPE_NOT_FOUND,
}
