package agalfioni.recipesai.ingredients_detector.presentation

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.presentation.extensions.ingredientsSuggestions
import agalfioni.recipesai.core.presentation.models.selectOrAdd
import agalfioni.recipesai.core.presentation.models.toSelectableList
import agalfioni.recipesai.core.presentation.utils.removeStressAccents
import agalfioni.recipesai.ingredients_detector.domain.use_cases.DetectIngredientsUseCase
import agalfioni.recipesai.ingredients_detector.domain.models.IngredientsResult
import agalfioni.recipesai.ingredients_detector.domain.interfaces.IngredientsRepository
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class IngredientsDetectorViewModel(
    private val imageUri: String,
    private val ingredientsRepository: IngredientsRepository,
    private val detectIngredientsUseCase: DetectIngredientsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(IngredientsDetectorUiState(imageUri = imageUri.toUri()))
    val uiState: StateFlow<IngredientsDetectorUiState> = _uiState.asStateFlow()

    private val _queryFlow = MutableStateFlow("")

    private val useIA = true

    init {
        viewModelScope.launch {
            ingredientsRepository
                .getLocalIngredients()
                .onSuccess { ingredientsList ->
                    val allLocalIngredients = ingredientsList.map {
                        if (Locale.getDefault().language == "es") {
                            it.es.removeStressAccents()
                        } else {
                            it.en
                        }
                    }
                    _uiState.update {
                        it.copy(
                            allLocalIngredients = allLocalIngredients
                        )
                    }
                }
        }

        viewModelScope.launch {
            if (useIA) {
                analyzeFridgeImage(imageUri)
            } else {
                _uiState.update { it.copy(isLoading = true) }
                delay(4000)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        detectedIngredients = IngredientsResult(
                            vegetables = listOf("Tomatoes", "Potatoes", "Carrots"),
                            fruits = listOf("Apples", "Bananas", "Oranges"),
                            dairy = listOf("Milk", "Cheese", "Yogurt"),
                            meat = listOf("Beef", "Chicken", "Pork"),
                            drinks = listOf("Water", "Juice", "Soda")
                        ).getAllIngredients()
                            .toSelectableList(true)
                            .sortedBy { it.item }
                    )
                }
            }
        }

        observeQuery()
    }
    fun onEvent(event: IngredientsDetectorEvent) {
        when (event) {
            is IngredientsDetectorEvent.OnIngredientSelectionChanged -> onIngredientSelectionChanged(event.item)
            is IngredientsDetectorEvent.OnQueryChanged -> onQueryChanged(event.query)
            is IngredientsDetectorEvent.OnSuggestionSelected -> onSuggestionSelected(event.suggestedItem)
        }
    }

    private fun onIngredientSelectionChanged(itemToToggle: String) {
        val newList = _uiState.value.detectedIngredients.map { selectable ->
            selectable.takeIf { it.item != itemToToggle }
                ?: selectable.copy(isSelected = !selectable.isSelected)
        }.sortedBy { it.item }

        _uiState.update { currentState ->
            currentState.copy(
                detectedIngredients = newList
            )
        }
    }

    fun analyzeFridgeImage(uriString: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = detectIngredientsUseCase(uriString)
            _uiState.update { currentState ->
                when (result) {
                    is AppResult.Success -> {
                        val selectedIngredients = result.data
                            .getAllIngredients()
                            .map { it.replaceFirstChar { it.titlecase() } }
                            .toSet()
                            .toSelectableList(true)
                            .sortedBy { it.item }

                        currentState.copy(
                            isLoading = false,
                            detectedIngredients = selectedIngredients,
                            error = null
                        )
                    }

                    is AppResult.Error -> {
                        currentState.copy(
                            isLoading = false,
                            error = result.error
                        )
                    }
                }
            }
        }
    }


    private fun observeQuery() {
        _queryFlow
            .ingredientsSuggestions { _uiState.value.allLocalIngredients }
            .onEach { matches ->
                _uiState.update { state ->
                    state.copy(
                        suggestions = matches,
                        showSuggestions = matches.isNotEmpty()
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun onQueryChanged(query: String) {
        _queryFlow.value = query
        _uiState.update {
            it.copy(query = query)
        }
    }

    fun onSuggestionSelected(item: String) {
        _uiState.update { state ->
            state.copy(
                query = "",
                showSuggestions = false,
                detectedIngredients = state
                    .detectedIngredients
                    .selectOrAdd(item)
                    .sortedBy { it.item }
            )
        }
        _queryFlow.value = ""
    }

}