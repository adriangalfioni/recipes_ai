package agalfioni.recipesai.home.presentation.home


import agalfioni.recipesai.core.presentation.extensions.ingredientsSuggestions
import agalfioni.recipesai.core.presentation.utils.removeStressAccents
import agalfioni.recipesai.home.domain.interfaces.IngredientsDetectorRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val ingredientsDetectorRepository: IngredientsDetectorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var _queryFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            ingredientsDetectorRepository
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
    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnIngredientRemoved -> onIngredientRemoved(event.item)
            is HomeEvent.OnQueryChanged -> onQueryChanged(event.query)
            is HomeEvent.OnSuggestionSelected -> onSuggestionSelected(event.suggestedItem)
            HomeEvent.OnClearAll -> _uiState.update { it.copy(addedIngredients = mutableListOf()) }
            HomeEvent.OnGenerateRecipes -> {}
        }
    }

    private fun onIngredientRemoved(itemToRemove: String) {
        _uiState.update { currentState ->
            val updatedList = currentState.addedIngredients.filter { it != itemToRemove }
            currentState.copy(addedIngredients = updatedList.toMutableList())
        }
    }

    fun onQueryChanged(query: String) {
        _queryFlow.value = query
        _uiState.update {
            it.copy(query = query)
        }
    }

    fun onSuggestionSelected(item: String) {
        _uiState.update { currentState ->
            val updatedList = currentState.addedIngredients
                .toSet()
                .plus(item)
                .sorted()
                .toMutableList()

            currentState.copy(
                query = "",
                showSuggestions = false,
                addedIngredients = updatedList
            )
        }
        _queryFlow.value = ""
    }

}