package agalfioni.recipesai.recipe_list.domain

sealed interface RecipeGenerationEvent {
    data object Started : RecipeGenerationEvent
    data object Completed : RecipeGenerationEvent
    data object Error : RecipeGenerationEvent
}