package agalfioni.recipesai.recipe.recipe_list.presentation

sealed interface RecipeListEvent {
    object OnRetry: RecipeListEvent
}