package agalfioni.recipesai.recipe.presentation.recipe_details.components

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RecipeHeader(recipe: agalfioni.recipesai.recipe.presentation.recipe_details.models.RecipeDetailsUi) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = recipe.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        // Horizontal Badges Row
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BadgeItem(icon = ImageVector.vectorResource(id = R.drawable.ic_time), text = recipe.minutesTime)
            //BadgeItem(icon = Icons.Outlined.Info, text = recipe.category)
            BadgeItem(icon = ImageVector.vectorResource(id = R.drawable.ic_fire_calories), text = recipe.totalCalories)
        }

        // AI Score Badge
        Surface(
            color = Color(0xFFB2F2BB),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_ai_star),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFF1B5E20)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.ai_score, recipe.aiScore),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )
            }
        }
    }
}

@Composable
fun BadgeItem(icon: ImageVector, text: String) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = CircleShape,
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(4.dp))
            Text(text, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Preview
@Composable
private fun RecipeDetailsScreenPreview() {
    RecipesAITheme {
        RecipeHeader(
            recipe = _root_ide_package_.agalfioni.recipesai.recipe.presentation.recipe_details.models.RecipeDetailsUi(
                id = "1_spaghetti_bolognese",
                title = "Spaghetti Bolognese",
                minutesTime = "20 min",
                category = "Vegan",
                totalCalories = "500 kcal",
                aiScore = 80,
                chefInsight = null,
                ingredients = listOf(),
                instructions = listOf()
            )
        )
    }
}