package agalfioni.recipesai.recipe_list.presentation.components

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.theme.GoodMatch
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.recipe_list.presentation.models.RecipeUi
import agalfioni.recipesai.recipe_list.presentation.preview_providers.RecipeUiProvider
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecipeCard(
    recipeUi: RecipeUi
) {
    val matchColor = if (recipeUi.isMatchHigh) {
        GoodMatch
    } else {
        MaterialTheme.colorScheme.outline
    }

    val cardContainerColor = if (recipeUi.isMatchHigh) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        Color.White
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = cardContainerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = recipeUi.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color(0xFF424945),
                    modifier = Modifier.size(28.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = matchColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.percentage_match, recipeUi.ingredientCoveragePercentage),
                    color = matchColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                InfoItem(icon = ImageVector.vectorResource(R.drawable.ic_time), text = recipeUi.minutesTime)
                Spacer(modifier = Modifier.width(20.dp))
                InfoItem(icon = ImageVector.vectorResource(R.drawable.ic_fork_spoon), text = stringResource(recipeUi.difficulty))
                Spacer(modifier = Modifier.width(20.dp))
                InfoItem(icon = ImageVector.vectorResource(R.drawable.ic_fire_calories), text = recipeUi.totalCalories)
            }
        }
    }
}

@Composable
fun InfoItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = GoodMatch,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Preview
@Composable
private fun RecipeCardPreview(
    @PreviewParameter(RecipeUiProvider::class) recipe: RecipeUi
) {
    RecipesAITheme {
        RecipeCard(
            recipeUi = recipe
        )
    }
}