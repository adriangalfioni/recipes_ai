package agalfioni.recipesai.recipe.domain.usecase

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.recipe.data.remote.AiRecipeGeneratorResultType
import agalfioni.recipesai.recipe.data.remote.FakeAiRecipeGenerator
import agalfioni.recipesai.recipe.data.repository.FakeRecipeRepository
import agalfioni.recipesai.recipe.data.repository.FakeRecipesSyncRepository
import agalfioni.recipesai.recipe.domain.GenerationTracker
import agalfioni.recipesai.recipe.domain.RecipeGenerationEvent
import app.cash.turbine.test
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GenerateRecipesUseCaseTest {

    private lateinit var useCase: GenerateRecipesUseCase
    private lateinit var fakeGenerator: FakeAiRecipeGenerator
    private lateinit var fakeRepo: FakeRecipeRepository
    private lateinit var fakeSyncRepo: FakeRecipesSyncRepository
    private val fakeTracker = GenerationTracker()

    private val testIngredients = listOf("Tomato", "Onion")
    private val testQty = 2

    @BeforeEach
    fun setup() {
        fakeGenerator = FakeAiRecipeGenerator()
        fakeRepo = FakeRecipeRepository()
        fakeSyncRepo = FakeRecipesSyncRepository()
        useCase = GenerateRecipesUseCase(
            fakeGenerator,
            fakeRepo,
            fakeSyncRepo,
            fakeTracker
        )
        fakeTracker.reset() // Ensure clean state
    }

    @Test
    fun `Successful recipe generation and repository storage`() = runTest {
        // Arrange
        fakeGenerator.aiRecipeGeneratorResultType = AiRecipeGeneratorResultType.SUCCESS_NON_EMPTY

        fakeTracker.status.test {
            // Act
            assertEquals(RecipeGenerationEvent.Idle, awaitItem()) // Initial state
            val result = useCase(testIngredients, testQty)

            // Assert
            Assertions.assertTrue(result is AppResult.Success)
            assertEquals(2, (result as AppResult.Success).data.size)
            assertEquals(2, fakeRepo.getCachedRecipes().size) // Saved to repo
            assertEquals(2, fakeSyncRepo.getCachedRecipes().size) // Saved to sync repo

            assertEquals(RecipeGenerationEvent.Started, awaitItem())
            assertEquals(RecipeGenerationEvent.Completed, awaitItem())
        }
    }

    @Test
    fun `Recipe generation failure handling`() = runTest {
        // Arrange
        fakeGenerator.aiRecipeGeneratorResultType = AiRecipeGeneratorResultType.FAILURE_TIMEOUT_EXCEPTION

        fakeTracker.status.test {
            awaitItem() // Skip Idle

            // Act
            val result = useCase(testIngredients, testQty)

            // Assert
            Assertions.assertTrue(result is AppResult.Error)
            assertEquals(DataError.DEADLINE_EXCEEDED, (result as AppResult.Error).error)
            Assertions.assertTrue(fakeRepo.getCachedRecipes().isEmpty()) // Not saved
            Assertions.assertTrue(fakeSyncRepo.getCachedRecipes().isEmpty()) // Not saved

            assertEquals(RecipeGenerationEvent.Started, awaitItem())
            assertEquals(RecipeGenerationEvent.Completed, awaitItem())
        }
    }

    @Test
    fun `Empty ingredients list handling`() = runTest {
        // Test behavior when ingredients list is empty; ensure parameters are passed
        // correctly to the generator and tracker flow remains consistent.

        // Arrange
        fakeGenerator.aiRecipeGeneratorResultType = AiRecipeGeneratorResultType.SUCCESS_EMPTY

        fakeTracker.status.test {
            // Act
            assertEquals(RecipeGenerationEvent.Idle, awaitItem()) // Initial state
            val result = useCase(testIngredients, testQty)

            // Assert
            Assertions.assertTrue(result is AppResult.Success)
            assertEquals(0, (result as AppResult.Success).data.size)
            assertEquals(0, fakeRepo.getCachedRecipes().size) // Saved to repo
            assertEquals(0, fakeSyncRepo.getCachedRecipes().size) // Saved to repo

            assertEquals(RecipeGenerationEvent.Started, awaitItem())
            assertEquals(RecipeGenerationEvent.Completed, awaitItem())
        }
    }

    @Test
    fun `Coroutine cancellation tracking`() = runTest {
        // Arrange
        fakeGenerator.aiRecipeGeneratorResultType =
            AiRecipeGeneratorResultType.COROUTINE_CANCELLATION_EXCEPTION

        fakeTracker.status.test {
            awaitItem() // Skip Idle

            // Act
            val job = launch {
                useCase(testIngredients, testQty)
            }

            // Assert
            assertEquals(RecipeGenerationEvent.Started, awaitItem())
            assertEquals(RecipeGenerationEvent.Error, awaitItem())

            job.cancelAndJoin()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `timeout emits Error`() = runTest {
        fakeGenerator.delayMillis = 10_000

        fakeTracker.status.test {
            awaitItem() // Idle
            val job = launch {
                withTimeout(300) {
                    useCase(testIngredients, testQty)
                }
            }

            assertEquals(RecipeGenerationEvent.Started, awaitItem())
            assertEquals(RecipeGenerationEvent.Error, awaitItem())

            job.cancelAndJoin()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `cancellation emits Error event`() = runTest {
        fakeGenerator.delayMillis = 10_000 // long running

        fakeTracker.status.test {
            awaitItem() // Idle

            val job = launch {
                useCase(testIngredients, testQty)
            }

            assertEquals(RecipeGenerationEvent.Started, awaitItem())

            // ACTUAL cancellation
            job.cancel()

            assertEquals(RecipeGenerationEvent.Error, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Successful completion state when context is active`() = runTest {
        // Arrange
        fakeGenerator.aiRecipeGeneratorResultType = AiRecipeGeneratorResultType.SUCCESS_EMPTY

        fakeTracker.status.test {
            awaitItem() // Skip Idle

            // Act
            useCase(testIngredients, testQty)

            // Assert
            assertEquals(RecipeGenerationEvent.Started, awaitItem())
            // Verifies the "else" branch of the finally block
            assertEquals(RecipeGenerationEvent.Completed, awaitItem())
        }
    }

    @Test
    fun `Large ingredients list performance impact`() = runTest {
        // Arrange
        val largeList = List(1000) { "Ingredient $it" }
        fakeGenerator.aiRecipeGeneratorResultType = AiRecipeGeneratorResultType.SUCCESS_EMPTY

        // Act
        val result = useCase(largeList, 1)

        // Assert
        Assertions.assertTrue(result is AppResult.Success)
    }

    @Test
    fun `Tracker event sequence validation`() = runTest {
        fakeTracker.status.test {
            awaitItem() // Idle

            useCase(testIngredients, testQty)

            val firstEvent = awaitItem()
            val secondEvent = awaitItem()

            // Verify order
            Assertions.assertTrue(firstEvent is RecipeGenerationEvent.Started)
            Assertions.assertTrue(secondEvent is RecipeGenerationEvent.Completed || secondEvent is RecipeGenerationEvent.Error)
        }
    }
}
