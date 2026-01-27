package agalfioni.recipesai.recipe_list.presentation

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import agalfioni.recipesai.recipe_list.presentation.components.AiProgressSection
import agalfioni.recipesai.recipe_list.presentation.components.RecipeCard
import agalfioni.recipesai.recipe_list.presentation.components.RecipeListTopBar
import agalfioni.recipesai.recipe_list.presentation.models.RecipeUi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel


@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onNavigateToRecipe: (Recipe) -> Unit,
    aiProgressViewModel: AiProgressViewModel = koinViewModel(),
    recipesListViewModel: RecipesListViewModel = koinViewModel()
) {
    val aiProgressUiState = aiProgressViewModel.uiState.collectAsStateWithLifecycle()
    val generateRecipesUiState = recipesListViewModel.uiState.collectAsStateWithLifecycle()

    RecipeListScreenRoot(
        onBackClick = onBackClick,
        onNavigateToRecipe= onNavigateToRecipe,
        aiProgressUiState = aiProgressUiState.value,
        generateRecipesUiState = generateRecipesUiState.value,
        onEvent = { event -> recipesListViewModel.onEvent(event) },
        modifier = modifier
    )
}

@Composable
fun RecipeListScreenRoot(
    onBackClick: () -> Unit,
    onNavigateToRecipe: (Recipe) -> Unit,
    aiProgressUiState: AiProgressState,
    generateRecipesUiState: GenerateRecipesUiState,
    onEvent: (RecipeListEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RecipeListTopBar(
                onBackClick = onBackClick
            )
        },
    ) { innerPadding ->

        generateRecipesUiState.navigateToRecipe?.let {
            LaunchedEffect(it) {
                onNavigateToRecipe(it)
                onEvent(RecipeListEvent.OnNavigationDone)
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = !aiProgressUiState.hasFinished
            ) {
                AiProgressSection(
                    aiProgressUiState = aiProgressUiState
                )
            }

            AnimatedVisibility(
                visible = aiProgressUiState.hasFinished
                        && generateRecipesUiState.recipes.isNotEmpty()
                        && generateRecipesUiState.error == null
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = rememberLazyListState()
                ) {
                    items(
                        items = generateRecipesUiState.recipes,
                        key = { it.title }
                    ) { recipe ->
                        RecipeCard(
                            recipeUi = recipe,
                            onClick = { onEvent(RecipeListEvent.OnRecipeClicked(recipe)) }
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
private fun RecipeListScreenLoadingPreview() {
    RecipesAITheme {
        RecipeListScreenRoot(
            onBackClick = {},
            onNavigateToRecipe = {},
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
            aiProgressUiState = AiProgressState(),
            generateRecipesUiState = GenerateRecipesUiState(),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun RecipeListScreenRecipesPreview() {
    val recipes = listOf(
        RecipeUi(
            title = "Spaghetti Bolognese",
            difficulty = R.string.difficulty_easy,
            minutesTime = "20 min",
            ingredientCoveragePercentage = "80%",
            isMatchHigh = true,
            totalCalories = "500 kcal"
        ),
        RecipeUi(
            title = "Beef Wellington",
            difficulty = R.string.difficulty_hard,
            minutesTime = "120 min",
            ingredientCoveragePercentage = "30%",
            isMatchHigh = false,
            totalCalories = "950 kcal"
        )
    )

    RecipesAITheme {
        RecipeListScreenRoot(
            onBackClick = {},
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
            aiProgressUiState = AiProgressState(hasFinished = true),
            generateRecipesUiState = GenerateRecipesUiState(
                recipes = recipes
            ),
            onEvent = {},
            onNavigateToRecipe = {}
        )
    }
}