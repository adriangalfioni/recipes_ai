package agalfioni.recipesai.scan_result.presentation

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.presentation.models.selectOrAdd
import agalfioni.recipesai.core.presentation.models.toSelectableList
import agalfioni.recipesai.scan_result.domain.IngredientsDetectorRepository
import agalfioni.recipesai.scan_result.domain.IngredientsResult
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.collections.emptyList

class IngredientsDetectorViewModel(
    private val imageUri: String,
    private val ingredientsDetectorRepository: IngredientsDetectorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(IngredientsDetectorUiState(imageUri = imageUri.toUri()))
    val uiState: StateFlow<IngredientsDetectorUiState> = _uiState.asStateFlow()

    private val queryFlow = MutableStateFlow("")

    private val useIA = false

    init {
        viewModelScope.launch {
            ingredientsDetectorRepository
                .getLocalIngredients()
                .onSuccess { ingredientsList ->
                    val allLocalIngredients = ingredientsList.map {
                        if (Locale.getDefault().language == "es") {
                            it.es
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
            _uiState.update { it.copy(isLoading = true) }
            delay(8000)
            _uiState.update { it.copy(isLoading = false) }
            if (useIA) {
                analyzeFridgeImage(imageUri.toUri())
            } else {
                _uiState.update {
                    it.copy(
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
            is IngredientsDetectorEvent.OnImageToAnalyze -> analyzeFridgeImage(event.imageUri)
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

    fun analyzeFridgeImage(uri: Uri) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = ingredientsDetectorRepository.analyzeFridge(uri)
            _uiState.update { currentState ->
                when (result) {
                    is AppResult.Success -> {
                        val selectedIngredients = result.data
                            .getAllIngredients()
                            .map { it.replaceFirstChar { it.titlecase() } }
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

    @OptIn(FlowPreview::class)
    private fun observeQuery() {
        queryFlow
            .debounce(300)
            .map { query ->
                if (query.isBlank()) {
                    emptyList()
                } else {
                    _uiState.value.allLocalIngredients
                        .filter { it.contains(query, ignoreCase = true) }
                        .take(4)
                }
            }
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
        queryFlow.value = query
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
        queryFlow.value = ""
    }

}