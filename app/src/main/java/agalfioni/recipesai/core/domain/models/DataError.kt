package agalfioni.recipesai.core.domain.models

enum class DataError : AppError {
    NETWORK_UNAVAILABLE, // No internet
    SERVER_ERROR, // 500 errors
    ACCESS_DENIED, // 403 / Permission denied
    NOT_FOUND, // 404 / Document missing
    UNKNOWN, // Something wildly unexpected
    UNAUTHENTICATED, // User not logged in
    RESOURCE_EXHAUSTED, // Rate limit / quota
    INVALID_ARGUMENT, // Bad data
    FAILED_PRECONDITION, // Invalid operation
    DEADLINE_EXCEEDED, // Timeout

    USER_NOT_LOGGED_IN,
}
