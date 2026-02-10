package agalfioni.recipesai.recipe.domain

sealed interface RecipeGenerationEvent {
    data object Idle : RecipeGenerationEvent
    data object Started : RecipeGenerationEvent
    data object Completed : RecipeGenerationEvent
    data object Error : RecipeGenerationEvent
}