package agalfioni.recipesai.home.presentation.home

import agalfioni.recipesai.core.domain.models.LocalIngredient
import agalfioni.recipesai.ingredients_detector.data.FakeIngredientsRepository
import agalfioni.recipesai.ingredients_detector.domain.interfaces.IngredientsRepository
import agalfioni.recipesai.recipe.data.repository.FakeRecipeRepository
import agalfioni.recipesai.recipe.domain.interfaces.RecipeRepository
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var ingredientsRepo: IngredientsRepository
    private lateinit var recipeRepo: RecipeRepository
    
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        ingredientsRepo = FakeIngredientsRepository()
        recipeRepo = FakeRecipeRepository()
        
        viewModel = HomeViewModel(ingredientsRepo, recipeRepo)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onQueryChanged updates uiState with new query`() = runTest {
        val newQuery = "Tomato"

        viewModel.uiState.test {
            awaitItem()

            viewModel.onEvent(HomeEvent.OnQueryChanged(newQuery))

            // Advance time to allow StateFlow combine to process
            runCurrent()

            val state = awaitItem()
            assertEquals(newQuery, state.query)
        }
    }

    @Test
    fun `onSuggestionSelected adds ingredient and clears query`() = runTest {
        val ingredient = "Onion"

        viewModel.uiState.test {
            awaitItem()

            viewModel.onEvent(HomeEvent.OnQueryChanged("Oni"))
            viewModel.onEvent(HomeEvent.OnSuggestionSelected(ingredient))

            runCurrent()

            val state = awaitItem()
            assert(state.addedIngredients.contains(ingredient))
            assertEquals("", state.query)
        }
    }

    @Test
    fun `OnClearAll removes all added ingredients`() = runTest {
        // Setup initial state with an ingredient
        viewModel.onEvent(HomeEvent.OnSuggestionSelected("Garlic"))
        runCurrent()
        
        viewModel.onEvent(HomeEvent.OnClearAll)
        runCurrent()

        viewModel.uiState.test {
            val state = awaitItem()
            assert(state.addedIngredients.isEmpty())
        }
    }

    @Test
    fun `onIngredientRemoved removes specific item`() = runTest {
        val item1 = "Apple"
        val item2 = "Banana"

        // 1. Start the collection first
        viewModel.uiState.test {
            // Skip the initial state (HomeUiState())
            awaitItem()

            // 2. Add items
            viewModel.onEvent(HomeEvent.OnSuggestionSelected(item1))
            // Assert items were added
            val stateWithTwo = awaitItem()
            assertEquals(listOf(item1), stateWithTwo.addedIngredients)

            viewModel.onEvent(HomeEvent.OnSuggestionSelected(item2))
            // In StateFlow, adding a second item will emit again
            val stateWithBoth = awaitItem()
            assertEquals(listOf(item1, item2).sorted(), stateWithBoth.addedIngredients)

            // 3. Perform the removal
            viewModel.onEvent(HomeEvent.OnIngredientRemoved(item1))

            // 4. Assert the final state
            val finalState = awaitItem()
            assert(!finalState.addedIngredients.contains(item1))
            assert(finalState.addedIngredients.contains(item2))

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `local ingredients logic correctly removes accents for Spanish locale`() = runTest {
        // We mock the local ingredient with an accent
        val input = listOf(LocalIngredient(id = "1", es = "Arroz con limón", en = "Lemon rice", type = ""))
        coEvery { ingredientsRepo.getLocalIngredients() } returns Result.success(input)

        // Note: If you can't easily change system Locale in tests,
        // this test assumes your environment/helper handles the "es" check.
        val vm = HomeViewModel(ingredientsRepo, recipeRepo)

        vm.uiState.test {
            val state = awaitItem()
            // Assuming "Arroz con limón".removeStressAccents() -> "Arroz con limon"
            val expected = "Arroz con limon"
            assert(state.allLocalIngredients.contains(expected))
        }
    }

    @Test
    fun `suggestions show only when query matches and is not blank`() = runTest {
        // Setup repo with a known list
        val localList = listOf("Tomato", "Onion", "Potato")
        coEvery { ingredientsRepo.getLocalIngredients() } returns Result.success(
            localList.mapIndexed { index, it -> LocalIngredient(id = index.toString(),en = it, es = it, type = "") }
        )

        val vm = HomeViewModel(ingredientsRepo, recipeRepo)

        vm.uiState.test {
            awaitItem() // Initial

            // Act: Type "Tom"
            vm.onEvent(HomeEvent.OnQueryChanged("Tom"))

            val state = awaitItem()
            assertEquals(listOf("Tomato"), state.suggestions)
            assertEquals(true, state.showSuggestions)

            // Act: Clear query
            vm.onEvent(HomeEvent.OnQueryChanged(""))
            val emptyState = awaitItem()
            assertEquals(false, emptyState.showSuggestions)
        }
    }

    @Test
    fun `uiState handles recipe repository errors gracefully`() = runTest {
        // Mock the recipe repo to throw an exception
        every { recipeRepo.getAllRecipes() } returns flow {
            throw RuntimeException("Database corruption")
        }

        val vm = HomeViewModel(ingredientsRepo, recipeRepo)

        vm.uiState.test {
            val state = awaitItem()
            // The combined state should still work, but recipes should be empty
            assert(state.recentRecipes.isEmpty())

            // Ensure other parts of the state still function
            vm.onEvent(HomeEvent.OnQueryChanged("Test"))
            assertEquals("Test", awaitItem().query)
        }
    }
}