import agalfioni.recipesai.core.domain.models.LocalIngredient
import agalfioni.recipesai.home.presentation.home.HomeEvent
import agalfioni.recipesai.home.presentation.home.HomeViewModel
import agalfioni.recipesai.ingredients_detector.domain.interfaces.IngredientsRepository
import agalfioni.recipesai.recipe.domain.interfaces.RecipeRepository
import androidx.paging.PagingData
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    // 1. Rule to swap the Main dispatcher for tests
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var ingredientsRepository: IngredientsRepository
    private lateinit var recipeRepository: RecipeRepository
    private lateinit var viewModel: HomeViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        ingredientsRepository = mockk()
        recipeRepository = mockk()

        // Default mock behaviors
        coEvery { ingredientsRepository.getLocalIngredients() } returns Result.success(emptyList())
        every { recipeRepository.getRecipes() } returns flowOf(PagingData.from(emptyList()))
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState initially shows default values`() = runTest {
        viewModel = HomeViewModel(ingredientsRepository, recipeRepository)

        viewModel.uiState.test {
            val initialState = awaitItem()
            assert(initialState.query.isEmpty())
            assert(initialState.addedIngredients.isEmpty())
            assert(initialState.allLocalIngredients.isEmpty())
            assert(initialState.error == null)
            assert(initialState.suggestions.isEmpty())
            assert(!initialState.showSuggestions)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onQueryChanged updates query and shows suggestions`() = runTest {
        // Setup local ingredients for the suggestion flow to work
        val local = listOf(
            LocalIngredient(en = "Tomato", es = "Tomate", id = "tomato", type = "")
        )
        coEvery { ingredientsRepository.getLocalIngredients() } returns Result.success(local)

        viewModel = HomeViewModel(ingredientsRepository, recipeRepository)
        // Advance to collect the local ingredients flow
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            // Skip initial state
            skipItems(1)

            viewModel.onEvent(HomeEvent.OnQueryChanged("Tom"))

            val state = awaitItem()
            assertEquals("Tom", state.query)
            // Note: This depends on your custom .ingredientsSuggestionsFlow logic
            assert(state.suggestions.isNotEmpty())
        }
    }

    @Test
    fun `onSuggestionSelected adds ingredient and clears query`() = runTest {
        viewModel = HomeViewModel(ingredientsRepository, recipeRepository)

        viewModel.uiState.test {
            skipItems(1)

            viewModel.onEvent(HomeEvent.OnSuggestionSelected("Onion"))

            val state = awaitItem()
            assert(state.addedIngredients.contains("Onion"))
            assertEquals("", state.query)
        }
    }

    @Test
    fun `OnClearAll removes all added ingredients`() = runTest {
        viewModel = HomeViewModel(ingredientsRepository, recipeRepository)

        viewModel.uiState.test {
            skipItems(1)

            // Manually add one first or trigger event
            viewModel.onEvent(HomeEvent.OnSuggestionSelected("Garlic"))
            skipItems(1)

            viewModel.onEvent(HomeEvent.OnClearAll)
            val state = awaitItem()
            assert(state.addedIngredients.isEmpty())
        }
    }

    @Test
    fun `OnIngredientRemoved remove ingredient`() = runTest {
        viewModel = HomeViewModel(ingredientsRepository, recipeRepository)

        viewModel.uiState.test {
            skipItems(1)

            viewModel.onEvent(HomeEvent.OnSuggestionSelected("Onion"))
            skipItems(1)

            viewModel.onEvent(HomeEvent.OnSuggestionSelected("Banana"))
            skipItems(1)

            viewModel.onEvent(HomeEvent.OnIngredientRemoved("Onion"))
            val state = awaitItem()

            assert(state.addedIngredients.size == 1)
            assert(state.addedIngredients.contains("Banana"))
        }
    }
}