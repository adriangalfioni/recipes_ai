package agalfioni.recipesai.recipe_list.presentation

import agalfioni.recipesai.recipe_list.presentation.models.RecipeUi

sealed interface RecipeListEvent {
    data class OnRecipeClicked(val recipeUi: RecipeUi): RecipeListEvent
}