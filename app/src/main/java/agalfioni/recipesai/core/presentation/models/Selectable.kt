package agalfioni.recipesai.core.presentation.models

data class Selectable<T>(
    val item: T,
    val isSelected: Boolean
)

fun <T> List<T>.toSelectableList(selected: Boolean): List<Selectable<T>>  =
    this.map { Selectable(it, selected) }

fun <T> List<Selectable<T>>.selectOrAdd(element: T): List<Selectable<T>> =
    if (any { it.item == element }) {
        map {
            if (it.item == element) it.copy(isSelected = true) else it
        }
    } else {
        this + Selectable(element, true)
    }
