package agalfioni.recipesai.core.presentation.utils

import agalfioni.recipesai.R
import agalfioni.recipesai.core.domain.models.AppResult
import agalfioni.recipesai.core.domain.models.DataError
import agalfioni.recipesai.core.presentation.utils.UiText.*

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.NETWORK_UNAVAILABLE -> StringResource(R.string.error_network_unavailable)
        DataError.SERVER_ERROR -> StringResource(R.string.error_server_error)
        DataError.ACCESS_DENIED -> StringResource(R.string.error_access_denied)
        DataError.NOT_FOUND -> StringResource(R.string.error_not_found)
        DataError.UNKNOWN -> StringResource(R.string.error_unknown)
        DataError.USER_NOT_LOGGED_IN -> StringResource(R.string.user_not_logged_in)
        DataError.UNAUTHENTICATED -> StringResource(R.string.user_not_logged_in)
        DataError.RESOURCE_EXHAUSTED -> StringResource(R.string.error_rate_limit_exceeded)
        DataError.INVALID_ARGUMENT -> StringResource(R.string.error_invalid_argument)
        DataError.FAILED_PRECONDITION -> StringResource(R.string.error_failed_precondition)
        DataError.DEADLINE_EXCEEDED -> StringResource(R.string.error_deadline_exceeded)
    }
}

fun AppResult.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}