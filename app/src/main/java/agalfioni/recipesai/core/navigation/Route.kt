package agalfioni.recipesai.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    @Serializable
    data object HomeScreen: Route

    @Serializable
    data class IngredientsDetectorScreen(val uri: String): Route
}