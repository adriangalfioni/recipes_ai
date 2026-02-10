package agalfioni.recipesai.ingredients_detector.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class IngredientsResult(
    val vegetables: List<String> = emptyList(),
    val fruits: List<String> = emptyList(),
    val dairy: List<String> = emptyList(),
    val meat: List<String> = emptyList(),
    val drinks: List<String> = emptyList(),
    val other: List<String> = emptyList()
) {
    // Helper to check if the fridge is totally empty
    fun isEmpty() = vegetables.isEmpty() && fruits.isEmpty() && dairy.isEmpty() &&
                    meat.isEmpty() && drinks.isEmpty() && other.isEmpty()

    // Total count for UI badges
    fun totalCount() = vegetables.size + fruits.size + dairy.size +
                       meat.size + drinks.size + other.size

    fun getAllIngredients() = vegetables + fruits + dairy + meat + drinks + other
}