package agalfioni.recipesai.recipe_list.presentation

import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.recipe_list.presentation.components.AiProgressSection
import agalfioni.recipesai.recipe_list.presentation.components.RecipeListTopBar
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel


@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    aiProgressViewModel: AiProgressViewModel = koinViewModel(),
    generateRecipesViewModel: GenerateRecipesViewModel = koinViewModel()
) {
    val aiProgressUiState = aiProgressViewModel.uiState.collectAsStateWithLifecycle()
    val generateRecipesUiState = generateRecipesViewModel.uiState.collectAsStateWithLifecycle()

    RecipeListScreenRoot(
        aiProgressUiState = aiProgressUiState.value,
        generateRecipesUiState = generateRecipesUiState.value,
        modifier = modifier
    )
}

@Composable
fun RecipeListScreenRoot(
    aiProgressUiState: AiProgressState,
    generateRecipesUiState: GenerateRecipesUiState,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RecipeListTopBar(
                onBackClick = {}
            )
        },
    ) { innerPadding ->

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

            }

        }
    }

}

@Preview
@Composable
private fun HomeScreenPreview() {
    RecipesAITheme {
        RecipeListScreenRoot(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
            aiProgressUiState = AiProgressState(),
            generateRecipesUiState = GenerateRecipesUiState()
        )
    }
}