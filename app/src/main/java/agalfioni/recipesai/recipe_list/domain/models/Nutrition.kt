package agalfioni.recipesai.recipe_list.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Nutrition(
    val calories: Double,
    @SerialName("total_fat")
    val totalFat: Double? = null,
    @SerialName("saturated_fat")
    val saturatedFat: Double? = null,
    val protein: Double? = null,
    val sodium: Double? = null,
    val potassium: Double? = null,
    @SerialName("dietary_fiber")
    val dietaryFiber: Double? = null,
    val cholesterol: Double? = null,
    val sugars: Double? = null,
    @SerialName("total_carbohydrate")
    val totalCarbohydrate: Double? = null
)
