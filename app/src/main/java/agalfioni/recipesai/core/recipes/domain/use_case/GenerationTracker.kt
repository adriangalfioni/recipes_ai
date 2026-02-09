package agalfioni.recipesai.core.recipes.domain.use_case

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