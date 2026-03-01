package agalfioni.recipesai.recipe.presentation.recipelist

sealed interface RecipeListEvent {
    object OnRetry : RecipeListEvent
}
