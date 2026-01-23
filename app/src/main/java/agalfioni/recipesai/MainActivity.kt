package agalfioni.recipesai

import agalfioni.recipesai.core.navigation.NavigationWrapper
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipesAITheme {
                NavigationWrapper()
            }
        }
    }
}
