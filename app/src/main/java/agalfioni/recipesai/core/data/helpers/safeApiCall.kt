package agalfioni.recipesai.core.data.helpers

import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import kotlinx.io.IOException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): AppResult<T, DataError> {
    return try {
        val response = apiCall()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                AppResult.Success(body)
            } else {
                AppResult.Error(DataError.UNKNOWN)
            }
        } else {
            AppResult.Error(response.code().toDataError())
        }

    } catch (e: UnknownHostException) {
        AppResult.Error(DataError.NETWORK_UNAVAILABLE)

    } catch (e: SocketTimeoutException) {
        AppResult.Error(DataError.DEADLINE_EXCEEDED)

    } catch (e: IOException) {
        // Covers SSLException, ConnectException, etc.
        AppResult.Error(DataError.NETWORK_UNAVAILABLE)

    } catch (e: Exception) {
        AppResult.Error(DataError.UNKNOWN)
    }
}

typealias HTTPCode = Int

private fun HTTPCode.toDataError(): DataError {
    return when (this) {
        400 -> DataError.INVALID_ARGUMENT
        401 -> DataError.UNAUTHENTICATED
        403 -> DataError.ACCESS_DENIED
        404 -> DataError.NOT_FOUND
        409 -> DataError.FAILED_PRECONDITION
        429 -> DataError.RESOURCE_EXHAUSTED
        in 500..599 -> DataError.SERVER_ERROR
        else -> DataError.UNKNOWN
    }
}