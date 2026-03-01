package agalfioni.recipesai.core.presentation.extensions

import agalfioni.recipesai.core.presentation.utils.removeStressAccents
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
fun Flow<String>.ingredientsSuggestions(ingredientsProvider: () -> List<String>): Flow<List<String>> =
    debounce(300)
        .mapLatest { query ->
            if (query.isBlank()) {
                emptyList()
            } else {
                ingredientsProvider()
                    .filter { it.removeStressAccents().contains(query, ignoreCase = true) }
                    .take(4)
            }
        }

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
fun Flow<String>.ingredientsSuggestionsFlow(ingredientsFlow: Flow<List<String>>): Flow<List<String>> =
    debounce(300)
        .flatMapLatest { query ->
            ingredientsFlow.map { ingredients ->
                if (query.isBlank()) {
                    emptyList()
                } else {
                    ingredients
                        .filter { it.removeStressAccents().contains(query, ignoreCase = true) }
                        .take(4)
                }
            }
        }
