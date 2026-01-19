package agalfioni.recipesai.core.domain.models

interface AppError

sealed interface AppResult<out D, out E: AppError> {
    data class Success<out D, out E: AppError>(val data: D): AppResult<D, E>
    data class Error<out D, out E: AppError>(val error: E): AppResult<D, E>
}

/**
 * Executes the [action] if the result is Success.
 * Returns the original AppResult for chaining.
 */
inline fun <D, E : AppError> AppResult<D, E>.onSuccess(action: (D) -> Unit): AppResult<D, E> {
    if (this is AppResult.Success) {
        action(data)
    }
    return this
}

/**
 * Executes the [action] if the result is Failure.
 * Returns the original AppResult for chaining.
 */
inline fun <D, E : AppError> AppResult<D, E>.onFailure(action: (E) -> Unit): AppResult<D, E> {
    if (this is AppResult.Error) {
        action(error)
    }
    return this
}

fun <D, E : AppError> AppResult<D, E>.getDataOrNull(): D? =
    when (this) {
        is AppResult.Success -> data
        is AppResult.Error -> null
    }
