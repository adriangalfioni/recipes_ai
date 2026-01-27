package agalfioni.recipesai.recipe_details.presentation.components

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.theme.DarkGrey
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.recipe_details.presentation.models.Ingredient
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IngredientItem(ingredient: Ingredient) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_circle_checkmark),
            contentDescription = "${ingredient.name} checkmark"
        )
        Text(
            text = ingredient.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.alignByBaseline()
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = ingredient.detail.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = DarkGrey,
            letterSpacing = 0.5.sp,
            modifier = Modifier.alignByBaseline()
        )
    }
}

@Preview
@Composable
private fun IngredientItemPreview() {
    RecipesAITheme {
        IngredientItem(
            Ingredient("Salt", "1 gr")
        )
    }
}