package agalfioni.recipesai.playground.presentation

import agalfioni.recipesai.playground.domain.DummyRepository
import agalfioni.recipesai.recipe.presentation.recipe_list.mappers.toRecipeUiList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class DummyViewModel(
    private val repository: DummyRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(DummyUiState())
    val uiState = _uiState.asStateFlow()

    val _query = MutableStateFlow("")

    /*init {
        viewModelScope.launch {
            repository
                .queryRecipes("asd")
                .onSuccess { recipes ->
                    _uiState.update {
                        it.copy(
                            listRecipes = recipes.toRecipeUiList()
                        )
                    }
                }
                .onFailure { error ->
                    //log error.message
                }
        }
    }*/

    init {
        _query
            // 1. Wait 300ms after the user stops typing to avoid spamming the API
            .debounce(300L)
            .onEach { _uiState.update { it.copy(isLoading = true) } }
            // 3. Switch to the API call. If a new query comes in, the old API call is cancelled.
           /* .flatMapConcat { query ->
                flow {
                    emit(repository.queryRecipes(query))
                }
            }*/
            .mapLatest { query ->
                 repository.queryRecipes(query)
            }
            // 4. Update the UI state based on the Result
            .onEach { result ->
                result.onSuccess { recipes ->
                    _uiState.update { it.copy(listRecipes = recipes.toRecipeUiList()) }
                }
                result.onFailure { /* log error */ }
            }
            // 5. Tie this to the ViewModel's lifecycle
            .launchIn(viewModelScope)
    }

    fun search(query: String) {
        _query.value = query
    }

}