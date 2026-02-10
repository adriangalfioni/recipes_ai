package agalfioni.recipesai.recipe.presentation.recipe_list

sealed interface RecipeListEvent {
    object OnRetry: RecipeListEvent
}