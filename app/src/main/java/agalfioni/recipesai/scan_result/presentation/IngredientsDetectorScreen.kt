package agalfioni.recipesai.scan_result.presentation

import agalfioni.recipesai.core.presentation.models.toSelectableList
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.scan_result.domain.IngredientsResult
import agalfioni.recipesai.scan_result.presentation.components.DetectedIngredientsChips
import agalfioni.recipesai.scan_result.presentation.components.IngredientsDetectorTopBar
import agalfioni.recipesai.scan_result.presentation.components.ScannedImage
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            ScannedImage(uiState.imageUri)
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
            DetectedIngredientsChips(
                ingredients = uiState.ingredients,
                onIngredientChipCLick = {
                    onEvent(IngredientsDetectorEvent.onIngredientSelectionChanged(it))
                }
            )
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
                ingredients = IngredientsResult(
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


