package agalfioni.recipesai.recipe_list.data.repository

import agalfioni.recipesai.core.data.helpers.AiJsonParser
import agalfioni.recipesai.recipe_list.data.local.RecipeDao
import agalfioni.recipesai.recipe_list.data.remote.AiRecipeGeneratorDataSource
import agalfioni.recipesai.recipe_list.domain.RecipeGenerationEvent
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.time.Duration.Companion.seconds

class GenerateRecipesRepositoryImplTest {

    // These are your actual dependencies
    /*private val dataSource = mockk<AiRecipeGeneratorDataSource>()
    private val dao = mockk<RecipeDao>(relaxed = true)
    private val parser = mockk<AiJsonParser>()

    // This is your actual code
    private val repository = GenerateRecipesRepositoryImpl(dataSource, dao, parser)

    @Test
    fun `when AI call takes too long, repository throws CancellationException`() = runTest {
        // GIVEN: The AI data source hangs for 10 seconds
        coEvery { dataSource.generateRecipes(any()) } coAnswers {
            delay(10_000)
            "{\"recipes\": []}"
        }

        // WHEN / THEN: We expect a TimeoutCancellationException to bubble up
        assertFailsWith<TimeoutCancellationException> {
            withTimeout(2.seconds) {
                repository.generateRecipes(1, listOf("Tomato"))
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when coroutine is cancelled, error event is emitted`() = runTest {
        // 1. Setup mocks
        val dataSource = mockk<AiRecipeGeneratorDataSource>(relaxed = true)
        val eventCollector = mutableListOf<RecipeGenerationEvent>()

        // 2. Make the data source hang so we have time to cancel
        coEvery { dataSource.generateRecipes(any<String>()) } coAnswers {
            delay(10_000)
            "data"
        }

        // When using runTest, since SharedFlow is a hot stream. We need to start
        // collecting it (using toList() or first()) before triggering the action,
        // otherwise, we'll miss the events.
        val job = launch {
            repository.generationEvents.toList(eventCollector)
        }

        val generationJob = launch {
            repository.generateRecipes(1, listOf("Tomato"))
        }

        // 4. Simulate user leaving the screen by cancelling the job
        advanceTimeBy(500) // Let it start
        generationJob.cancelAndJoin()

        // 5. Assertions
        // Your 'finally' block should have triggered the Error event
        assert(eventCollector.contains(RecipeGenerationEvent.Error))

        job.cancel() // Cleanup collector
    }*/
}