package agalfioni.recipesai.recipe_list.presentation

import agalfioni.recipesai.recipe_list.domain.GenerateRecipesRepository
import agalfioni.recipesai.recipe_list.domain.RecipeGenerationEvent
import agalfioni.recipesai.recipe_list.presentation.utils.AiProgressStepsGenerator
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AiProgressViewModel(
    repository: GenerateRecipesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiProgressState(steps = AiProgressStepsGenerator.generate()))
    val uiState: StateFlow<AiProgressState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.generationEvents
                .collect { event ->
                    when (event) {
                        is RecipeGenerationEvent.Started -> startProgress()
                        is RecipeGenerationEvent.Completed -> accelerateToFinish()
                    }
                }
        }
    }

    fun startProgress() {
        viewModelScope.launch {
            for ((index, step) in _uiState.value.steps.withIndex()) {
                if (_uiState.value.isAccelerating) return@launch

                _uiState.update { it.copy(stepIndex = index) }
                animateProgressTo(step.targetProgressPercentage, 8_000L)
            }
        }
    }

    fun accelerateToFinish(totalDurationMs: Long = 3_000L) {
        viewModelScope.launch {
            val current = _uiState.value.progress
            if (current >= 100) return@launch

            _uiState.update { it.copy(isAccelerating = true) }

            val remainingSteps = _uiState.value.steps
                .withIndex()
                .filter { it.value.targetProgressPercentage > current }

            val perStepDuration = totalDurationMs / remainingSteps.size

            for ((index, step) in remainingSteps) {
                _uiState.update { it.copy(stepIndex = index) }
                animateProgressTo(step.targetProgressPercentage, perStepDuration)
            }
        }
    }

    private suspend fun animateProgressTo(
        target: Int,
        durationMs: Long
    ) {
        val start = _uiState.value.progress
        val delta = target - start
        if (delta <= 0) return

        val delayPerStep = (durationMs / delta).coerceAtLeast(5L)

        repeat(delta) {
            _uiState.update { it.copy(progress = it.progress + 1) }
            delay(delayPerStep)
        }

        if (_uiState.value.progress >= 100) {
            _uiState.update {
                it.copy(hasFinished = true)
            }
        }
    }
}
