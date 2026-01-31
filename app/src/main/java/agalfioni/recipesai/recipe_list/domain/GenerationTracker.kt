package agalfioni.recipesai.recipe_list.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GenerationTracker {
    private val _status = MutableStateFlow<RecipeGenerationEvent>(RecipeGenerationEvent.Idle)
    val status = _status.asStateFlow()

    fun update(event: RecipeGenerationEvent) {
        _status.value = event
    }

    fun reset() {
        _status.value = RecipeGenerationEvent.Idle
    }
}