package agalfioni.recipesai.scan_result.presentation

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.presentation.models.toSelectableList
import agalfioni.recipesai.scan_result.domain.IngredientsDetectorRepository
import agalfioni.recipesai.scan_result.domain.IngredientsResult
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IngredientsDetectorViewModel(
    private val imageUri: String,
    private val ingredientsDetectorRepository: IngredientsDetectorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(IngredientsDetectorUiState(imageUri = imageUri.toUri()))
    val uiState: StateFlow<IngredientsDetectorUiState> = _uiState.asStateFlow()

    private val useIA = false

    fun onEvent(event: IngredientsDetectorEvent) {
        when (event) {
            is IngredientsDetectorEvent.onImageToAnalyze -> analyzeFridgeImage(event.imageUri)
            is IngredientsDetectorEvent.onIngredientSelectionChanged -> {
                onIngredientSelectionChanged(event.item)
            }
        }
    }

    private fun onIngredientSelectionChanged(itemToToggle: String) {
        val newList = _uiState.value.ingredients.map { selectable ->
            selectable.takeIf { it.item != itemToToggle }
                ?: selectable.copy(isSelected = !selectable.isSelected)
        }

        _uiState.update { currentState ->
            currentState.copy(
                ingredients = newList
            )
        }
    }

    init {
        if (useIA) {
            analyzeFridgeImage(imageUri.toUri())
        } else {
            _uiState.update {
                it.copy(
                    ingredients = IngredientsResult(
                        vegetables = listOf("Tomatoes", "Potatoes", "Carrots"),
                        fruits = listOf("Apples", "Bananas", "Oranges"),
                        dairy = listOf("Milk", "Cheese", "Yogurt"),
                        meat = listOf("Beef", "Chicken", "Pork"),
                        drinks = listOf("Water", "Juice", "Soda")
                    ).getAllIngredients().toSelectableList(true)
                )
            }
        }
    }

    fun analyzeFridgeImage(uri: Uri) {
        viewModelScope.launch {
            // 1. Set loading state
            _uiState.update { it.copy(isLoading = true, error = null) }

            // 2. Call the repository
            val result = ingredientsDetectorRepository.analyzeFridge(uri)

            // 3. Update state based on AppResult
            _uiState.update { currentState ->
                when (result) {
                    is AppResult.Success -> {
                        val selectedIngredients = result.data
                            .getAllIngredients()
                            .toSelectableList(true)

                        currentState.copy(
                            isLoading = false,
                            ingredients = selectedIngredients,
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

}