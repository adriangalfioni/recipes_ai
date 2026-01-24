package agalfioni.recipesai.home.presentation.home

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.components.SearchableWithSuggestions
import agalfioni.recipesai.core.presentation.components.rememberCameraLauncher
import agalfioni.recipesai.core.presentation.models.toSelectableList
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.home.presentation.components.GenerateRecipesBottomBar
import agalfioni.recipesai.home.presentation.home.components.MediaSourcePickerSheet
import agalfioni.recipesai.home.presentation.home.components.ScanFridgeCard
import agalfioni.recipesai.home.presentation.home.models.ImageSource
import agalfioni.recipesai.home.presentation.components.DetectedIngredientsChips
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onImage: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenRoot(
        uiState = uiState.value,
        onEvent = viewModel::onEvent,
        onImage = onImage,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenRoot(
    uiState: HomeUiState,
    onEvent: (event: HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
    onImage: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            GenerateRecipesBottomBar(
                selectedIngredientsQty = uiState.addedIngredients.size,
                onGenerateRecipesClick = {
                    onEvent(HomeEvent.OnGenerateRecipes)
                }
            )
        },
    ) { innerPadding ->

        val scrollState = rememberScrollState()
        val scope = rememberCoroutineScope()
        val density = LocalDensity.current
        val topOffsetPx = with(density) { 64.dp.toPx() }
        var showImageSourceSheet by remember { mutableStateOf(false) }

        val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let { onImage(uri.toString()) }
        }

        val cameraLauncher = rememberCameraLauncher(
            onImageCaptured = { uri ->
                onImage(uri.toString())
            }
        )

        Column(
            modifier = modifier
                .verticalScroll(scrollState)
                .imePadding()
                .padding(horizontal = 16.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(Modifier.height(36.dp))
            ScanFridgeCard(
                onScanClick = {
                    showImageSourceSheet = true
                }
            )

            if (showImageSourceSheet) {
                MediaSourcePickerSheet(
                    onDismissRequest = { showImageSourceSheet = false },
                    onSourceSelected = { imageSource ->
                        showImageSourceSheet = false
                        when (imageSource) {
                            ImageSource.CAMERA -> {
                                cameraLauncher()
                            }

                            ImageSource.GALLERY -> {
                                galleryLauncher.launch("image/*")
                            }
                        }
                    }
                )
            }
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Add Ingredients",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Clear All"/*stringResource(R.string.clear_all)*/,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.clickable(
                        interactionSource = null,
                        indication = null,
                        onClick = { onEvent(HomeEvent.OnClearAll) }
                    )
                )
            }
            SearchableWithSuggestions(
                value = uiState.query,
                placeholder = stringResource(R.string.add_more_ingredients),
                onValueChange = { onEvent(HomeEvent.OnQueryChanged(it)) },
                suggestions = uiState.suggestions,
                onSelectSuggestion = { onEvent(HomeEvent.OnSuggestionSelected(it)) },
                trailingIcon = Icons.Default.Add,
                onTrailingClick = { onEvent(HomeEvent.OnSuggestionSelected(it)) },
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
                ingredients = uiState.addedIngredients.toList().toSelectableList(true),
                onTrailingIconClick = {
                    onEvent(HomeEvent.OnIngredientRemoved(it))
                }
            )
        }
    }

}

@Preview
@Composable
private fun HomeScreenPreview() {
    RecipesAITheme {
        HomeScreenRoot(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
            onImage = {},
            onEvent = {},
            uiState = HomeUiState()
        )
    }
}