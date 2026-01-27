package agalfioni.recipesai.core.navigation

import androidx.compose.runtime.snapshots.SnapshotStateList

fun <T> SnapshotStateList<T>.keepOnlyFirst() {
    if (size > 1) subList(1, size).clear()
}
