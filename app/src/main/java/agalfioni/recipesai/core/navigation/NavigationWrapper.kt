package agalfioni.recipesai.core.navigation

import agalfioni.recipesai.home.presentation.home.HomeScreen
import agalfioni.recipesai.home.presentation.scan_result.IngredientDetectorScreen
import agalfioni.recipesai.home.presentation.scan_result.IngredientsDetectorViewModel
import agalfioni.recipesai.recipe_details.presentation.RecipeDetailsScreen
import agalfioni.recipesai.recipe_details.presentation.RecipeDetailsViewModel
import agalfioni.recipesai.recipe_list.domain.models.Recipe
import agalfioni.recipesai.recipe_list.presentation.RecipeListScreen
import agalfioni.recipesai.recipe_list.presentation.RecipesListViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavigationWrapper(
    modifier: Modifier = Modifier
) {
    val backStack = rememberSaveable(
        saver = listSaver(
            save = { it.toList() },
            restore = { it.toMutableStateList() }
        )
    ) {
        mutableStateListOf<NavKey>(Route.HomeScreen)
    }
    val resultStore = rememberResultStore()

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            // Add the default decorators for managing scenes and saving state
            rememberSaveableStateHolderNavEntryDecorator(),
            // Then add the view model store decorator
            rememberViewModelStoreNavEntryDecorator()
        ),
        onBack = {
            when {
                backStack.lastOrNull() == Route.RecipeListScreen -> backStack.keepOnlyFirst()
                else -> backStack.removeLastOrNull()
            }
        },
        entryProvider = { key ->
            when (key) {
                is Route.HomeScreen -> NavEntry(key) {
                    HomeScreen(
                        resultStore = resultStore,
                        onImage = { backStack.add(Route.IngredientsDetectorScreen(it)) },
                        onGenerateRecipesClick = {
                            resultStore.setResult("Ingredients", it)
                            backStack.add(Route.RecipeListScreen)
                        }
                    )
                }

                is Route.IngredientsDetectorScreen -> NavEntry(key) {
                    val viewModel = koinViewModel<IngredientsDetectorViewModel> {
                        parametersOf(key.uri)
                    }

                    IngredientDetectorScreen(
                        viewModel = viewModel,
                        onBackClick = { backStack.removeLastOrNull() },
                        onGenerateRecipesClick = {
                            resultStore.setResult("Ingredients", it)
                            backStack.add(Route.RecipeListScreen)
                        }
                    )
                }

                is Route.RecipeListScreen -> NavEntry(key) {
                    val ingredients = resultStore.getResultAndRemove<List<String>>("Ingredients")

                    val viewModel = koinViewModel<RecipesListViewModel> {
                        parametersOf(ingredients)
                    }

                    RecipeListScreen(
                        onBackClick = { backStack.keepOnlyFirst() },
                        onNavigateToRecipe = { recipeId ->
                            backStack.add(Route.RecipeDetailsScreen(recipeId))
                        },
                        recipesListViewModel = viewModel
                    )
                }

                is Route.RecipeDetailsScreen -> NavEntry(key) {
                    val viewModel = koinViewModel<RecipeDetailsViewModel> {
                        parametersOf(key.recipeId)
                    }

                    RecipeDetailsScreen(
                        onBackClick = { backStack.removeLastOrNull() },
                        recipeDetailsViewModel = viewModel
                    )
                }

                else -> {
                    error("Unknown route: $key")
                }
            }
        }
    )

}