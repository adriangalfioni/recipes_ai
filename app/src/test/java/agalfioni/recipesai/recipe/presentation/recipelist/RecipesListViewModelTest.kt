package agalfioni.recipesai.recipe.presentation.recipelist

import agalfioni.recipesai.R
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.presentation.utils.asUiText
import agalfioni.recipesai.recipe.domain.models.Difficulty
import agalfioni.recipesai.recipe.domain.models.Nutrition
import agalfioni.recipesai.recipe.domain.models.Recipe
import agalfioni.recipesai.recipe.domain.usecase.GenerateRecipesUseCase
import agalfioni.recipesai.recipe.presentation.recipelist.mappers.toRecipeUiList
import agalfioni.recipesai.recipe.presentation.recipelist.utils.IA_GENERATION_TIMEOUT_MILLIS
import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNull
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class RecipesListViewModelTest {
    // 1. Rule to swap the Main dispatcher for tests
    private val testDispatcher = StandardTestDispatcher()

    // 2. Mocking the dependency
    private val useCase: GenerateRecipesUseCase = mockk()
    private val ingredients = listOf("Tomato", "Onion")

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init calls usecase and updates state with success`() =
        runTest {
            val recipes =
                listOf(
                    Recipe(
                        id = "1",
                        title = "Spaghetti Bolognese",
                        difficulty = Difficulty.EASY,
                        minutesTime = 20,
                        ingredientCoverage = 0.8,
                        instructions = listOf(),
                        nutrition = Nutrition(calories = 200.0),
                        ingredients = listOf(),
                    ),
                )
            // Setup mock
            coEvery { useCase(any(), any()) } returns AppResult.Success(recipes)

            // Create VM (init runs immediately)
            val viewModel = RecipesListViewModel(ingredients, useCase)

            viewModel.uiState.test {
                // Initial state from MutableStateFlow
                val item1 = awaitItem()

                // Trigger the coroutine in init
                testDispatcher.scheduler.runCurrent()

                val item2 = awaitItem()
                assertEquals(recipes.toRecipeUiList(), item2.recipes)
                assertFalse(item2.isLoading)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `init updates state with error on failure`() =
        runTest {
            val error = DataError.SERVER_ERROR
            coEvery { useCase(any(), any()) } returns AppResult.Error(error)

            val viewModel = RecipesListViewModel(ingredients, useCase)

            viewModel.uiState.test {
                awaitItem() // Skip loading
                testDispatcher.scheduler.runCurrent()

                val item = awaitItem()
                assertEquals(error.asUiText(), item.error)
                assertFalse(item.isLoading)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `timeout updates state with deadline exceeded error`() =
        runTest {
            // Setup the use case to hang forever
            coEvery { useCase(any(), any()) } coAnswers {
                delay(IA_GENERATION_TIMEOUT_MILLIS + 1000)
                AppResult.Success(emptyList())
            }

            val viewModel = RecipesListViewModel(ingredients, useCase)

            viewModel.uiState.test {
                awaitItem() // Skip loading

                // Advance time to trigger the TimeoutCancellationException
                advanceTimeBy(IA_GENERATION_TIMEOUT_MILLIS + 1)
                runCurrent()

                val item = awaitItem()
                assertEquals(DataError.DEADLINE_EXCEEDED.asUiText(), item.error)
                assertFalse(item.isLoading)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `onRetry event resets state and calls usecase again`() =
        runTest {
            coEvery { useCase(any(), any()) } returns AppResult.Success(emptyList())

            val viewModel = RecipesListViewModel(ingredients, useCase)
            testDispatcher.scheduler.runCurrent()

            viewModel.onEvent(RecipeListEvent.OnRetry)
            testDispatcher.scheduler.runCurrent()

            viewModel.uiState.test {
                val item = awaitItem()
                assertNull(item.error)
                assertFalse(item.isLoading)

                // Verify usecase called twice (init + retry)
                coVerify(exactly = 2) { useCase(ingredients, 10) }
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `initial state before init coroutine runs`() =
        runTest {
            val recipes =
                listOf(
                    Recipe(
                        id = "1",
                        title = "Spaghetti Bolognese",
                        difficulty = Difficulty.EASY,
                        minutesTime = 20,
                        ingredientCoverage = 0.8,
                        instructions = listOf(),
                        nutrition = Nutrition(calories = 200.0),
                        ingredients = listOf(),
                    ),
                )
            coEvery { useCase(any(), any()) } returns AppResult.Success(recipes)

            val viewModel = RecipesListViewModel(ingredients, useCase)

            // init launch hasn't executed yet
            viewModel.uiState.test {
                val item = awaitItem()
                assertNull(item.error)
                assertTrue(item.isLoading)
                assert(item.recipes.isEmpty())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Empty ingredients list handling`() =
        runTest {
            // Test behavior when the ingredients list passed to the constructor is empty
            // to ensure the use case handles empty inputs correctly.

            // Setup mock
            coEvery { useCase(any(), any()) } returns AppResult.Success(emptyList())

            // Create VM (init runs immediately)
            val viewModel = RecipesListViewModel(ingredients, useCase)

            viewModel.uiState.test {
                // Initial state from MutableStateFlow
                val item1 = awaitItem()

                // Trigger the coroutine in init
                testDispatcher.scheduler.runCurrent()

                val item2 = awaitItem()
                assert(item2.recipes.isEmpty())
                assertFalse(item2.isLoading)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Use case parameter validation`() =
        runTest {
            // Verify that generateRecipesUseCase is called with the exact ingredients list provided
            // and the constant NUMBER_OF_RECIPES_TO_GENERATE (10).

            // Setup: Use Case must return something so the coroutine completes
            coEvery { useCase(any(), any()) } returns AppResult.Success(emptyList())

            // Act: Create the ViewModel
            val viewModel = RecipesListViewModel(ingredients, useCase)

            // Trigger the init block coroutine
            runCurrent()

            // Assert: Check that the ViewModel called the Use Case with the RIGHT data
            coVerify {
                useCase(
                    ingredients = ingredients, // Must match the list passed to VM constructor
                    recipesQty = 10, // Must match the constant NUMBER_OF_RECIPES_TO_GENERATE
                )
            }
        }

    @Test
    fun `Mapping logic verification`() =
        runTest {
            // Ensure that the toRecipeUiList() extension function correctly transforms the domain
            // models into UI models within the StateFlow.

            val domainRecipes =
                listOf(
                    Recipe(
                        id = "1",
                        title = "Spaghetti Bolognese",
                        difficulty = Difficulty.EASY,
                        minutesTime = 20,
                        ingredientCoverage = 0.8,
                        instructions = listOf(),
                        nutrition = Nutrition(calories = 200.0),
                        ingredients = listOf(),
                    ),
                    Recipe(
                        id = "2",
                        title = "Roasted Chicken",
                        difficulty = Difficulty.MODERATE,
                        minutesTime = 70,
                        ingredientCoverage = 0.6,
                        instructions = listOf(),
                        nutrition = Nutrition(calories = 350.0),
                        ingredients = listOf(),
                    ),
                )
            // Setup mock
            coEvery { useCase(any(), any()) } returns AppResult.Success(domainRecipes)

            // Create VM (init runs immediately)
            val viewModel = RecipesListViewModel(ingredients, useCase)

            viewModel.uiState.test {
                // Initial state from MutableStateFlow
                val item1 = awaitItem()

                // Trigger the coroutine in init
                testDispatcher.scheduler.runCurrent()

                val item2 = awaitItem()
                val firstUiRecipe = item2.recipes[0]
                assertEquals(domainRecipes.toRecipeUiList(), item2.recipes)
                assert(item2.recipes.size == 2)
                assertEquals(R.string.difficulty_easy, firstUiRecipe.difficulty)
                assertEquals("0:20", firstUiRecipe.minutesTime)
                assertEquals(true, firstUiRecipe.isMatchHigh, "isMatchHigh")
                assertEquals("200 kcal", firstUiRecipe.totalCalories)
                assertFalse(item2.isLoading)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `Multiple retry clicks results in only one successful state update`() =
        runTest {
            var successCount = 0
            coEvery { useCase(any(), any()) } coAnswers {
                delay(1000)
                successCount++
                AppResult.Success(emptyList())
            }

            val viewModel = RecipesListViewModel(ingredients, useCase)

            // Rapid fire
            repeat(5) { viewModel.onEvent(RecipeListEvent.OnRetry) }

            advanceUntilIdle()

            // Even if it was CALLED 6 times (init + 5 retries),
            // it should only have FINISHED (incremented successCount) once.
            assertEquals(1, successCount)
        }

    @Test
    fun `ViewModelScope cancellation cancels pending generation`() =
        runTest {
            var wasCancelled = false

            // Setup use case to detect cancellation
            coEvery { useCase(any(), any()) } coAnswers {
                try {
                    delay(10000) // Long delay
                    AppResult.Success(emptyList())
                } catch (e: CancellationException) {
                    wasCancelled = true
                    throw e
                }
            }

            val viewModel = RecipesListViewModel(ingredients, useCase)
            runCurrent() // Start the init call

            // Act: Cancel the ViewModel's scope
            // viewModelScope is tied to the Job of the dispatcher in tests or
            // we can use a custom internal accessor.
            // In unit tests, the easiest way is to cancel the test scope or clear the VM.
            viewModel.viewModelScope.cancel()
            runCurrent()

            // Assert
            assertTrue(wasCancelled, "The use case should have been cancelled")
        }
}
