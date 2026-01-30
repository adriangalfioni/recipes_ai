package agalfioni.recipesai.recipe_list.presentation

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.components.LottieAnimation
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.core.presentation.utils.UiText
import agalfioni.recipesai.recipe_list.presentation.components.AiProgressSection
import agalfioni.recipesai.recipe_list.presentation.components.RecipeCard
import agalfioni.recipesai.recipe_list.presentation.components.RecipeListTopBar
import agalfioni.recipesai.recipe_list.presentation.models.RecipeUi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel


@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onNavigateToRecipe: (String) -> Unit,
    aiProgressViewModel: AiProgressViewModel = koinViewModel(),
    recipesListViewModel: RecipesListViewModel = koinViewModel()
) {
    val aiProgressUiState = aiProgressViewModel.uiState.collectAsStateWithLifecycle()
    val generateRecipesUiState = recipesListViewModel.uiState.collectAsStateWithLifecycle()

    RecipeListScreenRoot(
        onBackClick = onBackClick,
        onNavigateToRecipe = onNavigateToRecipe,
        aiProgressUiState = aiProgressUiState.value,
        generateRecipesUiState = generateRecipesUiState.value,
        onEvent = { event -> recipesListViewModel.onEvent(event) },
        modifier = modifier
    )
}

@Composable
fun RecipeListScreenRoot(
    onBackClick: () -> Unit,
    onNavigateToRecipe: (String) -> Unit,
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

        if (generateRecipesUiState.error != null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val errorMsg = generateRecipesUiState.error.asString()
                Image(
                    painterResource(R.drawable.recipe_error),
                    contentDescription = errorMsg,
                    modifier = Modifier.size(250.dp),
                )
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = errorMsg,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = { onEvent(RecipeListEvent.OnRetry) },
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    Text("Retry")
                }
            }
        } else {
            AnimatedVisibility(
                visible = !aiProgressUiState.hasFinished
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(bottom = 72.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LottieAnimation(
                        animationRes = R.raw.ingredients_animation
                    )
                    AiProgressSection(
                        aiProgressUiState = aiProgressUiState
                    )
                }
            }

            AnimatedVisibility(
                visible = aiProgressUiState.hasFinished
                        && generateRecipesUiState.recipes.isNotEmpty()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    state = rememberLazyListState()
                ) {
                    items(
                        items = generateRecipesUiState.recipes,
                        key = { it.title }
                    ) { recipe ->
                        RecipeCard(
                            recipeUi = recipe,
                            onClick = { onNavigateToRecipe(recipe.id) }
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
            id = "1",
            title = "Spaghetti Bolognese",
            difficulty = R.string.difficulty_easy,
            minutesTime = "20 min",
            ingredientCoveragePercentage = "80%",
            isMatchHigh = true,
            totalCalories = "500 kcal"
        ),
        RecipeUi(
            id = "2",
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

@Preview
@Composable
private fun RecipeListScreenErrorPreview() {
    RecipesAITheme {
        RecipeListScreenRoot(
            onBackClick = {},
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
            aiProgressUiState = AiProgressState(hasFinished = true),
            generateRecipesUiState = GenerateRecipesUiState(
                error = UiText.DynamicString("Something went wrong")
            ),
            onEvent = {},
            onNavigateToRecipe = {}
        )
    }
}