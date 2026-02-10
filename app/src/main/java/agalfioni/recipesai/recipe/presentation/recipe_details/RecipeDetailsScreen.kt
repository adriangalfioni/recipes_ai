package agalfioni.recipesai.recipe.presentation.recipe_details

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.components.RecipesAiTopBar
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.core.presentation.utils.UiText
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
        var isIngredientsExpanded by remember { mutableStateOf(true) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                recipeDetailsState.error != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val errorMsg = recipeDetailsState.error.asString()
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
                    }
                }

                recipeDetailsUi != null -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 20.dp)
                    ) {

                        item {
                            _root_ide_package_.agalfioni.recipesai.recipe.presentation.recipe_details.components.RecipeHeader(
                                recipeDetailsUi
                            )
                        }
                        recipeDetailsUi.chefInsight?.let {
                            item {
                                _root_ide_package_.agalfioni.recipesai.recipe.presentation.recipe_details.components.AIInsightCard(
                                    recipeDetailsUi.chefInsight
                                )
                            }
                        }
                        // Ingredients Section
                        item {
                            _root_ide_package_.agalfioni.recipesai.recipe.presentation.recipe_details.components.IngredientSectionHeader(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .clickable(
                                        interactionSource = null,
                                        indication = null,
                                        onClick = { isIngredientsExpanded = !isIngredientsExpanded }
                                    ),
                                title = stringResource(R.string.ingredients),
                                badgeText = stringResource(
                                    R.string.ingredient_items_count,
                                    recipeDetailsUi.ingredients.size
                                ),
                                sectionExpanded = isIngredientsExpanded
                            )
                        }
                        item {
                            AnimatedVisibility(
                                visible = isIngredientsExpanded,
                                enter = expandVertically() + fadeIn(),
                                exit = shrinkVertically() + fadeOut()
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    recipeDetailsUi.ingredients.forEach { ingredient ->
                                        _root_ide_package_.agalfioni.recipesai.recipe.presentation.recipe_details.components.IngredientItem(
                                            ingredient
                                        )
                                    }
                                }
                            }
                        }

                        // Instructions Section
                        item {
                            Text(
                                stringResource(R.string.instructions),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        item {
                            Column {
                                recipeDetailsUi.instructions.forEach { instruction ->
                                    val isLast = recipeDetailsUi.instructions.last() == instruction
                                    _root_ide_package_.agalfioni.recipesai.recipe.presentation.recipe_details.components.InstructionItem(
                                        instruction,
                                        isLast
                                    )
                                }
                            }
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
                recipeDetailsUi = _root_ide_package_.agalfioni.recipesai.recipe.presentation.recipe_details.preview_providers.dummyRecipeDetailsUi
            )
        )
    }
}

@Preview
@Composable
private fun RecipeDetailsScreenErrorPreview() {
    RecipesAITheme {
        RecipeDetailsScreenRoot(
            onBackClick = {},
            recipeDetailsState = RecipeDetailsState(
                error = UiText.DynamicString("Recipe not found")
            )
        )
    }
}