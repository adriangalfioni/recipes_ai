package agalfioni.recipesai.recipe.recipe_list.presentation

import agalfioni.recipesai.core.recipes.domain.use_case.GenerationTracker
import agalfioni.recipesai.core.recipes.domain.use_case.RecipeGenerationEvent
import agalfioni.recipesai.recipe.recipe_list.presentation.utils.AiProgressStepsGenerator
import agalfioni.recipesai.recipe.recipe_list.presentation.utils.IA_GENERATION_TIMEOUT_MILLIS
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AiProgressViewModel(
    private val generationTracker: GenerationTracker
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        _root_ide_package_.agalfioni.recipesai.recipe.recipe_list.presentation.AiProgressState(
            steps = AiProgressStepsGenerator.generate()
        )
    )
    val uiState: StateFlow<agalfioni.recipesai.recipe.recipe_list.presentation.AiProgressState> = _uiState.asStateFlow()

    private var progressJob: Job? = null

    init {
        viewModelScope.launch {
            generationTracker.status
                .collect { event ->
                    when (event) {
                        is RecipeGenerationEvent.Started -> startProgress()
                        is RecipeGenerationEvent.Completed -> accelerateToFinish()
                        is RecipeGenerationEvent.Error -> setFinished()
                        is RecipeGenerationEvent.Idle -> resetProgress()
                    }
                }
        }
    }

    private fun resetProgress() {
        progressJob?.cancel()
        _uiState.update {
            it.copy(
                hasFinished = false,
                progress = 0,
                stepIndex = 0,
                isAccelerating = false
            )
        }
    }

    private fun setFinished() {
        progressJob?.cancel()
        _uiState.update {
            it.copy(
                hasFinished = true,
                progress = 100,
                isAccelerating = false
            )
        }
    }

    fun startProgress() {
        progressJob?.cancel()
        _uiState.update {
            it.copy(
                hasFinished = false,
                progress = 0,
                isAccelerating = false,
                stepIndex = 0
            )
        }
        progressJob = viewModelScope.launch {
            for ((index, step) in _uiState.value.steps.withIndex()) {
                _uiState.update { it.copy(stepIndex = index) }
                val stepDuration = obtainStepDuration(step.targetProgressPercentage)
                animateProgressTo(step.targetProgressPercentage, stepDuration)
            }
        }
    }

    private fun obtainStepDuration(targetProgress: Int): Long {
        val currentProgress = _uiState.value.progress
        val delta = targetProgress - currentProgress
        return delta * IA_GENERATION_TIMEOUT_MILLIS / 100
    }

    fun accelerateToFinish(totalDurationMs: Long = 3_000L) {
        val currentProgress = _uiState.value.progress
        if (currentProgress >= 100) return

        progressJob?.cancel()
        progressJob = viewModelScope.launch {
            _uiState.update { it.copy(isAccelerating = true) }

            val remainingSteps = _uiState.value.steps
                .withIndex()
                .filter { it.value.targetProgressPercentage > currentProgress }

            if (remainingSteps.isEmpty()) {
                animateProgressTo(100, totalDurationMs)
                return@launch
            }

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
            _uiState.update { it.copy(progress = (it.progress + 1).coerceAtMost(100)) }
            delay(delayPerStep)
        }

        if (_uiState.value.progress >= 100) {
            _uiState.update {
                it.copy(hasFinished = true)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        generationTracker.reset()
    }
}
