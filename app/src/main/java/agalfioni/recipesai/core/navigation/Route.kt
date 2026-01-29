package agalfioni.recipesai.core.navigation

import android.os.Parcelable
import androidx.navigation3.runtime.NavKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey, Parcelable {

    @Parcelize
    @Serializable
    data object HomeScreen: Route

    @Parcelize
    @Serializable
    data class IngredientsDetectorScreen(val uri: String): Route

    @Parcelize
    @Serializable
    object RecipeListScreen: Route

    @Parcelize
    @Serializable
    data class RecipeDetailsScreen(val recipeId: String): Route
}