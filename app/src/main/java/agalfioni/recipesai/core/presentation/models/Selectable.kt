package agalfioni.recipesai.core.presentation.models

data class Selectable<T>(
    val item: T,
    val isSelected: Boolean
)

fun <T> List<T>.toSelectableList(selected: Boolean): List<Selectable<T>>  =
    this.map { Selectable(it, selected) }

