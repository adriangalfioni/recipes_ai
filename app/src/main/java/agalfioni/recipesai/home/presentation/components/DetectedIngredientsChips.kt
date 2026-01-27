package agalfioni.recipesai.home.presentation.components

import agalfioni.recipesai.core.presentation.models.Selectable
import agalfioni.recipesai.core.presentation.models.toSelectableList
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.home.domain.IngredientsResult
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DetectedIngredientsChips(
    ingredients: List<Selectable<String>>,
    modifier: Modifier = Modifier,
    onIngredientChipClick: (String) -> Unit = {},
    onTrailingIconClick: ((String) -> Unit)? = null
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ingredients.forEach { selectableIngredient ->
            IngredientSelectableChip(
                selectableIngredient,
                onChipClick = onIngredientChipClick,
                onTrailingIconClick = onTrailingIconClick
            )
        }
    }
}

@Preview
@Composable
private fun DetectedIngredientsChipsPreview() {
    RecipesAITheme {
        Box(Modifier.background(color = MaterialTheme.colorScheme.background)) {
            DetectedIngredientsChips(
                IngredientsResult(
                    vegetables = listOf("Tomatoes", "Potatoes", "Carrots"),
                    fruits = listOf("Apples", "Bananas", "Oranges"),
                    dairy = listOf("Milk", "Cheese", "Yogurt"),
                    meat = listOf("Beef", "Chicken", "Pork"),
                    drinks = listOf("Water", "Juice", "Soda")
                ).getAllIngredients().toSelectableList(true),
                onIngredientChipClick = {},
            )
        }
    }
}