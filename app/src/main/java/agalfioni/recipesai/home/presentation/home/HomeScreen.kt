package agalfioni.recipesai.home.presentation.home

import agalfioni.recipesai.R
import agalfioni.recipesai.core.navigation.ResultStore
import agalfioni.recipesai.core.presentation.components.SearchableWithSuggestions
import agalfioni.recipesai.core.presentation.components.rememberCameraLauncher
import agalfioni.recipesai.core.presentation.models.toSelectableList
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.home.presentation.components.DetectedIngredientsChips
import agalfioni.recipesai.home.presentation.components.GenerateRecipesBottomBar
import agalfioni.recipesai.home.presentation.home.components.MediaSourcePickerSheet
import agalfioni.recipesai.home.presentation.home.components.ScanFridgeCard
import agalfioni.recipesai.home.presentation.home.models.ImageSource
import agalfioni.recipesai.recipe.presentation.recipelist.components.RecipeCard
import agalfioni.recipesai.recipe.presentation.recipelist.models.RecipeUi
import agalfioni.recipesai.recipe.presentation.recipelist.previewproviders.RecipeUiListProvider
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    resultStore: ResultStore,
    onImage: (String) -> Unit,
    onGenerateRecipesClick: (List<String>) -> Unit,
    onNavigateToRecipe: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val recentRecipes = viewModel.recentRecipes.collectAsLazyPagingItems()

    HomeScreenRoot(
        uiState = uiState.value,
        recentRecipes = recentRecipes,
        onEvent = viewModel::onEvent,
        onImage = onImage,
        onNavigateToRecipe = onNavigateToRecipe,
        onGenerateRecipesClick = onGenerateRecipesClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenRoot(
    uiState: HomeUiState,
    recentRecipes: LazyPagingItems<RecipeUi>,
    onEvent: (event: HomeEvent) -> Unit,
    onImage: (String) -> Unit,
    onNavigateToRecipe: (String) -> Unit,
    onGenerateRecipesClick: (List<String>) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier =
            Modifier
                .fillMaxSize(),
        bottomBar = {
            GenerateRecipesBottomBar(
                selectedIngredientsQty = uiState.addedIngredients.size,
                onGenerateRecipesClick = {
                    onGenerateRecipesClick(uiState.addedIngredients.toList())
                },
            )
        },
    ) { innerPadding ->

        val scrollState = rememberScrollState()
        val density = LocalDensity.current
        var showImageSourceSheet by remember { mutableStateOf(false) }

        val galleryLauncher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
            ) { uri: Uri? ->
                uri?.let { onImage(uri.toString()) }
            }

        val cameraLauncher =
            rememberCameraLauncher(
                onImageCaptured = { uri ->
                    onImage(uri.toString())
                },
            )

        Column(
            modifier =
                modifier
                    .imePadding()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp)
                    .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(Modifier.height(36.dp))
            ScanFridgeCard(
                onScanClick = {
                    showImageSourceSheet = true
                },
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
                    },
                )
            }
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.add_ingredients),
                    style = MaterialTheme.typography.titleLarge,
                )
                if (uiState.addedIngredients.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.clear_all),
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier =
                            Modifier.clickable(
                                interactionSource = null,
                                indication = null,
                                onClick = { onEvent(HomeEvent.OnClearAll) },
                            ),
                    )
                }
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
            )
            Spacer(modifier = Modifier.height(16.dp))
            DetectedIngredientsChips(
                ingredients = uiState.addedIngredients.toList().toSelectableList(true),
                onTrailingIconClick = {
                    onEvent(HomeEvent.OnIngredientRemoved(it))
                },
            )

            RecentRecipes(
                recentRecipes = recentRecipes,
                onEvent = onEvent,
                onRecipeCardClick = onNavigateToRecipe,
            )
        }
    }
}

@Composable
fun RecentRecipes(
    recentRecipes: LazyPagingItems<RecipeUi>,
    onEvent: (event: HomeEvent) -> Unit,
    onRecipeCardClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (recentRecipes.itemCount > 0) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.recent_recipes),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            /*Text(
                text = stringResource(R.string.view_history),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = {  }
                )
            )*/
        }
        LazyRow(
            modifier =
                Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            items(
                count = recentRecipes.itemCount,
                key = recentRecipes.itemKey { it.id },
            ) { index ->
                recentRecipes[index]?.let { recipe ->
                    RecipeCard(
                        recipeUi = recipe,
                        modifier =
                            Modifier
                                .fillParentMaxHeight()
                                .fillParentMaxWidth(0.85f)
                                .widthIn(max = 300.dp),
                        showMatchIndicator = false,
                        onClick = { onRecipeCardClick(recipe.id) },
                    )
                }
            }

            when (recentRecipes.loadState.append) {
                is LoadState.Loading -> {
                    item(contentType = "loader") {
                        CircularProgressIndicator()
                    }
                }
                else -> Unit
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(RecipeUiListProvider::class) recipes: List<RecipeUi>,
) {
    RecipesAITheme {
        val pagingData = remember { PagingData.from(recipes) }

        val lazyPagingItems =
            flowOf(pagingData)
                .collectAsLazyPagingItems()

        HomeScreenRoot(
            uiState = HomeUiState(),
            recentRecipes = lazyPagingItems,
            onEvent = {},
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface),
            onImage = {},
            onNavigateToRecipe = {},
            onGenerateRecipesClick = {},
        )
    }
}
