package agalfioni.recipesai.home.presentation.components

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.components.RecipeRoundedButton
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateRecipesBottomBar(
    selectedIngredientsQty: Int,
    onGenerateRecipesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            //.navigationBarsPadding()
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
        shadowElevation = 8.dp
    )
    {
        Column(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.navigationBars)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.selected_ingredients, selectedIngredientsQty),
                style = MaterialTheme.typography.titleMedium
            )
            RecipeRoundedButton(
                text = stringResource(R.string.generate_recipes),
                onClick = onGenerateRecipesClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .shadow(
                        elevation = 16.dp,     // Depth of the shadow
                        shape = CircleShape,  // The shadow will follow this shape
                        clip = false          // Set to false so the shadow isn't cut off
                    ),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_ai_star),
                        contentDescription = null
                    )
                },
                enabled = selectedIngredientsQty > 0
            )
        }

    }
}

@Preview
@Composable
private fun GenerateRecipesToolbarPreview() {
    RecipesAITheme {
        Box(modifier = Modifier.background(color = Color.Blue)) {

            GenerateRecipesBottomBar(
                selectedIngredientsQty = 6,
                onGenerateRecipesClick = {}
            )
        }
    }
}
