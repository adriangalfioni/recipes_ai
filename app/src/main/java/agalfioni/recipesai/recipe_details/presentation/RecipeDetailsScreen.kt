package agalfioni.recipesai.recipe_details.presentation

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.components.RecipesAiTopBar
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.recipe_details.presentation.components.AIInsightCard
import agalfioni.recipesai.recipe_details.presentation.components.IngredientItem
import agalfioni.recipesai.recipe_details.presentation.components.InstructionItem
import agalfioni.recipesai.recipe_details.presentation.components.RecipeHeader
import agalfioni.recipesai.recipe_details.presentation.components.SectionHeader
import agalfioni.recipesai.recipe_details.presentation.preview_providers.dummyRecipeDetailsUi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel


@Composable
fun RecipeDetailsScreen(
    onBackClick: () -> Unit,
    recipeDetailsViewModel: RecipeDetailsViewModel = koinViewModel()
) {
    val recipeDetailsState = recipeDetailsViewModel.uiState.collectAsStateWithLifecycle()

    RecipeDetailsScreenRoot(
        onBackClick = onBackClick,
        recipeDetailsState = recipeDetailsState.value,
        /*onEvent = { event -> recipesListViewModel.onEvent(event) },
        modifier = modifier*/
    )
}

@Composable
fun RecipeDetailsScreenRoot(
    onBackClick: () -> Unit,
    recipeDetailsState: RecipeDetailsState,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            RecipesAiTopBar(
                title = stringResource(R.string.recipe_details),
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        val recipeDetailsUi = recipeDetailsState.recipeDetailsUi

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            recipeDetailsUi?.let {
                item { RecipeHeader(recipeDetailsUi) }
                recipeDetailsUi.chefInsight?.let {
                    item { AIInsightCard(recipeDetailsUi.chefInsight) }
                }
                // Ingredients Section
                item {
                    SectionHeader(
                        modifier = Modifier.padding(top = 8.dp),
                        title = "Ingredients",
                        badgeText = "${recipeDetailsUi.ingredients.size} items")
                }
                items(recipeDetailsUi.ingredients) { ingredient ->
                    IngredientItem(ingredient)
                }

                // Instructions Section
                item { Text("Instructions", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold) }
                item {
                    Column {
                        recipeDetailsUi.instructions.forEach { instruction ->
                            val isLast = recipeDetailsUi.instructions.last() == instruction
                            InstructionItem(instruction, isLast)
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun RecipeDetailsScreenPreview() {
    RecipesAITheme {
        RecipeDetailsScreenRoot(
            onBackClick = {},
            recipeDetailsState = RecipeDetailsState(
                recipeDetailsUi = dummyRecipeDetailsUi
            )
        )
    }
}