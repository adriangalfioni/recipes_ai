package agalfioni.recipesai.core.data.helpers

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import com.google.ai.client.generativeai.type.GoogleGenerativeAIException
import com.google.ai.client.generativeai.type.InvalidAPIKeyException
import com.google.ai.client.generativeai.type.InvalidStateException
import com.google.ai.client.generativeai.type.PromptBlockedException
import com.google.ai.client.generativeai.type.QuotaExceededException
import com.google.ai.client.generativeai.type.RequestTimeoutException
import com.google.ai.client.generativeai.type.ResponseStoppedException
import com.google.ai.client.generativeai.type.SerializationException
import com.google.ai.client.generativeai.type.ServerException
import com.google.ai.client.generativeai.type.UnsupportedUserLocationException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

suspend fun <T> safeAiCall(call: suspend () -> T): AppResult<T, DataError> =
    try {
        val result = call()
        AppResult.Success(result)
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        e.printStackTrace()

        val mappedError =
            when (e) {
                // Mapping the specific SDK exceptions you provided
                is QuotaExceededException -> DataError.RESOURCE_EXHAUSTED
                is InvalidAPIKeyException -> DataError.UNAUTHENTICATED
                is UnsupportedUserLocationException -> DataError.ACCESS_DENIED
                is PromptBlockedException -> DataError.INVALID_ARGUMENT
                is ResponseStoppedException -> DataError.SERVER_ERROR
                is RequestTimeoutException -> DataError.DEADLINE_EXCEEDED
                is ServerException -> DataError.SERVER_ERROR
                is SerializationException -> DataError.INVALID_ARGUMENT
                is InvalidStateException -> DataError.UNKNOWN

                // Generic catch for any other AI SDK issues
                is GoogleGenerativeAIException -> DataError.SERVER_ERROR

                // Standard networking or unexpected issues
                else -> DataError.UNKNOWN
            }
        AppResult.Error(mappedError)
    }
