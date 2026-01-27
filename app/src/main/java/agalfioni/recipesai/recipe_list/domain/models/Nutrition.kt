package agalfioni.recipesai.recipe_list.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Nutrition(
    val calories: Double,
    @SerialName("total_fat")
    val totalFat: Double?,
    @SerialName("saturated_fat")
    val saturatedFat: Double?,
    val protein: Double?,
    val sodium: Double?,
    val potassium: Double?,
    @SerialName("dietary_fiber")
    val dietaryFiber: Double?,
    val cholesterol: Double?,
    val sugars: Double?,
    @SerialName("total_carbohydrate")
    val totalCarbohydrate: Double?
)
