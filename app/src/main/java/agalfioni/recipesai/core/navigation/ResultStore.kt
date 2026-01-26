package agalfioni.recipesai.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable

@Stable
class ResultStore {
    private val results = mutableMapOf<Any, Any?>()

    @Suppress("UNCHECKED_CAST")
    fun <T> getResult(key: Any): T? = results[key] as? T

    @Suppress("UNCHECKED_CAST")
    fun <T> getResultAndRemove(key: Any): T? {
        val result = results[key] as? T
        results.remove(key)
        return result
    }

    fun <T> setResult(key: Any, value: T) {
        results[key] = value
    }

    fun removeResult(key: Any) {
        results.remove(key)
    }

    companion object {
        val Saver = Saver<ResultStore, Map<Any, Any?>>(
            save = { it.results.toMap() },
            restore = { ResultStore().apply {
                results.putAll(it)
            } }
        )
    }
}

@Composable
fun rememberResultStore() = rememberSaveable(
    saver = ResultStore.Saver
) {
    ResultStore()
}