package agalfioni.recipesai.recipe_list.presentation

sealed interface RecipeListEvent {
    object OnRetry: RecipeListEvent
}