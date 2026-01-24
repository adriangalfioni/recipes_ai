package agalfioni.recipesai.home.presentation.scan_result

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.components.SearchableWithSuggestions
import agalfioni.recipesai.core.presentation.models.toSelectableList
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.home.domain.IngredientsResult
import agalfioni.recipesai.home.presentation.scan_result.components.AiLoadingPulse
import agalfioni.recipesai.home.presentation.scan_result.components.DetectedIngredientsChips
import agalfioni.recipesai.home.presentation.components.GenerateRecipesBottomBar
import agalfioni.recipesai.home.presentation.scan_result.components.IngredientsDetectorTopBar
import agalfioni.recipesai.home.presentation.scan_result.components.ScannedImage
import android.net.Uri
import androidx.compose.foundation.background
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
    viewModel: IngredientsDetectorViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    IngredientDetectorRoot(
        uiState = uiState.value,
        onEvent = viewModel::onEvent,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientDetectorRoot(
    uiState: IngredientsDetectorUiState,
    onEvent: (event: IngredientsDetectorEvent) -> Unit,
    onBackClick: () -> Unit,
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
                    onEvent(IngredientsDetectorEvent.OnImageToAnalyze(uiState.imageUri))
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
                .verticalScroll(scrollState)
                .fillMaxSize()
                /*.background(color = MaterialTheme.colorScheme.surface)*/
                .imePadding()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            ScannedImage(
                imageUri = uiState.imageUri,
                scanCompleted = !uiState.isLoading
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Detected Ingredients:",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = "Check the items detected by AI before adding them to your list.",
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
                    onIngredientChipCLick = {
                        onEvent(IngredientsDetectorEvent.OnIngredientSelectionChanged(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}

/*@Composable
fun GlowActionButton(onClick: () -> Unit) {
    val glowColor = MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .size(72.dp) // Slightly larger to account for the glow spread
            .drawBehind {
                // Drawing the "Glow" manually behind the button
                drawShadow(
                    color = glowColor.copy(alpha = 0.4f),
                    borderRadius = 100.dp, // Fully rounded
                    blurRadius = 20.dp,    // The "spread" of the glow
                    offsetY = 4.dp
                )
            }
            .background(MaterialTheme.colorScheme.primary, CircleShape)
            .clip(CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_ai_star),
            contentDescription = "Analyze",
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
    }
}*/

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
            onBackClick = {}
        )
    }
}


