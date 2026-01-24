package agalfioni.recipesai.scan_result.presentation.components

import agalfioni.recipesai.core.presentation.models.Selectable
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IngredientSelectableChip(
    item: Selectable<String>,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    colors: ButtonColors = ButtonDefaults.buttonColors().copy(
        containerColor = MaterialTheme.colorScheme.primary.copy(
            alpha = 0.1f
        )
    )
) {
    OutlinedButton(
        shape = RoundedCornerShape(10.dp),
        onClick = { onClick(item.item) },
        colors = colors.copy(
            containerColor = if (item.isSelected) {
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.alignByBaseline(),
                text = item.item,
                color = textColor
            )
            AnimatedVisibility(item.isSelected) {
                Icon(
                    modifier = Modifier
                        .size(22.dp)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Preview
@Composable
private fun IngredientSelectableChipPreview() {
    RecipesAITheme {
        IngredientSelectableChip(
            item = Selectable("Tomatoes", true),
            onClick = {},

        )
    }
}