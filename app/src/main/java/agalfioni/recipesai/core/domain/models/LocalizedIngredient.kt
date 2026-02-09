package agalfioni.recipesai.core.domain.models

data class LocalizedIngredient(
    val id: String,
    val en: String,
    val es: String,
    val type: String
)