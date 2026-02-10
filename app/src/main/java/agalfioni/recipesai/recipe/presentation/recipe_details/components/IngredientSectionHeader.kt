package agalfioni.recipesai.recipe.presentation.recipe_details.components

import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IngredientSectionHeader(
    title: String,
    badgeText: String,
    modifier: Modifier = Modifier,
    sectionExpanded: Boolean = true
) {
    val arrowRotation by animateFloatAsState(
        targetValue = if (sectionExpanded) 0f else 180f,
        label = "ArrowRotation"
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier.rotate(arrowRotation)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Surface(
            color = Color(0xFFF1F3F1),
            shape = CircleShape
        ) {
            Text(
                text = badgeText,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
private fun RecipeDetailsScreenPreview() {
    RecipesAITheme {
        IngredientSectionHeader(
            title = "Ingredients",
            badgeText = "5 items"
        )
    }
}