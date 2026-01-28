package agalfioni.recipesai.core.presentation.utils

data class UiOneTimeEvent<T>(
    private val payload: T,
    private val onEventConsumed: () -> Unit
) {
    fun consumePayload(): T {
        return payload.also {
            onEventConsumed()
        }
    }
}
