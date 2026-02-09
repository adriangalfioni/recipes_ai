package agalfioni.recipesai.ingredients_detector.presentation

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.components.SearchableWithSuggestions
import agalfioni.recipesai.core.presentation.models.toSelectableList
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.core.recipes.presentation.mappers.components.DetectedIngredientsChips
import agalfioni.recipesai.core.recipes.presentation.mappers.components.GenerateRecipesBottomBar
import agalfioni.recipesai.core.scan.domain.model.IngredientsResult
import agalfioni.recipesai.ingredients_detector.presentation.components.AiLoadingPulse
import agalfioni.recipesai.ingredients_detector.presentation.components.IngredientsDetectorTopBar
import agalfioni.recipesai.ingredients_detector.presentation.components.ScannedImage
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun IngredientDetectorScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onGenerateRecipesClick: (List<String>) -> Unit,
    viewModel: IngredientsDetectorViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    IngredientDetectorRoot(
        uiState = uiState.value,
        onEvent = viewModel::onEvent,
        onBackClick = onBackClick,
        onGenerateRecipesClick = onGenerateRecipesClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientDetectorRoot(
    uiState: IngredientsDetectorUiState,
    onEvent: (event: IngredientsDetectorEvent) -> Unit,
    onBackClick: () -> Unit,
    onGenerateRecipesClick: (List<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            IngredientsDetectorTopBar(
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            GenerateRecipesBottomBar(
                selectedIngredientsQty = uiState.detectedIngredients.filter { it.isSelected }.size,
                onGenerateRecipesClick = {
                    onGenerateRecipesClick(uiState.detectedIngredients.filter { it.isSelected }.map { it.item })
                }
            )
        },
    ) { innerPadding ->

        val scope = rememberCoroutineScope()
        val scrollState = rememberScrollState()
        val density = LocalDensity.current
        val topOffsetPx = with(density) { 64.dp.toPx() }

        Column(
            modifier = Modifier
                .imePadding()
                .verticalScroll(scrollState)
                .fillMaxSize()
                /*.background(color = MaterialTheme.colorScheme.surface)*/
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            ScannedImage(
                imageUri = uiState.imageUri,
                scanCompleted = !uiState.isLoading
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.detected_ingredients),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = stringResource(R.string.check_detected_by_ai),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (uiState.isLoading) {
                AiLoadingPulse()
            } else {
                SearchableWithSuggestions(
                    value = uiState.query,
                    placeholder = stringResource(R.string.add_more_ingredients),
                    onValueChange = { onEvent(IngredientsDetectorEvent.OnQueryChanged(it)) },
                    suggestions = uiState.suggestions,
                    onSelectSuggestion = { onEvent(IngredientsDetectorEvent.OnSuggestionSelected(it)) },
                    trailingIcon = Icons.Default.Add,
                    onTrailingClick = { onEvent(IngredientsDetectorEvent.OnSuggestionSelected(it)) },
                    leadingIcon = null,
                    maxSuggestions = 4,
                    onFocusedAtY = { yInRoot ->
                        scope.launch {
                            val targetScroll = (
                                    scrollState.value +
                                            yInRoot -
                                            topOffsetPx
                                    ).toInt().coerceAtLeast(0)

                            scrollState.animateScrollTo(targetScroll)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                DetectedIngredientsChips(
                    ingredients = uiState.detectedIngredients,
                    onIngredientChipClick = {
                        onEvent(IngredientsDetectorEvent.OnIngredientSelectionChanged(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun IngredientDetectorScreenPreview() {
    RecipesAITheme {
        IngredientDetectorRoot(
            uiState = IngredientsDetectorUiState(
                imageUri = Uri.EMPTY,
                detectedIngredients = IngredientsResult(
                    vegetables = listOf("Tomatoes", "Potatoes", "Carrots"),
                    fruits = listOf("Apples", "Bananas", "Oranges"),
                    dairy = listOf("Milk", "Cheese", "Yogurt"),
                    meat = listOf("Beef", "Chicken", "Pork"),
                    drinks = listOf("Water", "Juice", "Soda")
                ).getAllIngredients().toSelectableList(true)
            ),
            onEvent = {},
            onBackClick = {},
            onGenerateRecipesClick = {}
        )
    }
}


