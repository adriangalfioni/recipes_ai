package agalfioni.recipesai.core.presentation.components

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RecipeRoundedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    leadingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
) {
    Button(
        modifier = modifier,
        shape = CircleShape,
        onClick = onClick,
        colors = colors,
        enabled = enabled,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            leadingIcon?.invoke()
            Text(
                text = text,
            )
        }
    }
}

@Composable
fun RecipeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        colors = colors,
    ) {
        Text(
            text = text,
        )
    }
}

@Composable
fun RecipeSelectableButton(
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    RecipeButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        colors =
            ButtonDefaults.buttonColors().copy(
                containerColor =
                    if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
            ),
    )
}

@Composable
fun RecipeOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    colors: ButtonColors =
        ButtonDefaults.buttonColors().copy(
            containerColor =
                MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.1f,
                ),
        ),
) {
    OutlinedButton(
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        colors = colors,
        border =
            BorderStroke(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outline,
            ),
    ) {
        Text(
            text = text,
            color = textColor,
        )
    }
}

@Composable
fun RecipeSelectableOutlinedButton(
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    RecipeOutlinedButton(
        text = text,
        textColor =
            if (isSelected) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                MaterialTheme.colorScheme.outline
            },
        onClick = onClick,
        modifier = modifier,
        colors =
            ButtonDefaults.buttonColors().copy(
                containerColor =
                    if (isSelected) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
            ),
    )
}

@Preview
@Composable
private fun RecipeRoundedButtonPreview() {
    RecipesAITheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
            RecipeRoundedButton(
                text = "15 mins (easy)",
                onClick = {},
                modifier =
                    Modifier
                        .width(250.dp)
                        .height(60.dp),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_ai_star),
                        contentDescription = null,
                    )
                },
            )
        }
    }
}

@Preview
@Composable
private fun RecipeButtonPreview() {
    RecipesAITheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
            RecipeButton(
                text = "15 mins (easy)",
                onClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun RecipeSelectableButtonPreview() {
    RecipesAITheme {
        RecipeSelectableButton(
            text = "15 mins (easy)",
            onClick = {},
            isSelected = true,
        )
    }
}

@Preview
@Composable
private fun RecipeSelectableButtonPreview2() {
    RecipesAITheme {
        RecipeSelectableButton(
            text = "15 mins (easy)",
            onClick = {},
            isSelected = false,
        )
    }
}

@Preview
@Composable
private fun RecipeOutlinedButtonPreview() {
    RecipesAITheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
            RecipeOutlinedButton(
                text = "15 mins (easy)",
                onClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun RecipeOutlinedSelectableButtonPreview() {
    RecipesAITheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
            RecipeSelectableOutlinedButton(
                text = "15 mins (easy)",
                onClick = {},
                isSelected = true,
            )
        }
    }
}

@Preview
@Composable
private fun RecipeOutlinedSelectableButtonPreview2() {
    RecipesAITheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
            RecipeSelectableOutlinedButton(
                text = "15 mins (easy)",
                onClick = {},
                isSelected = false,
            )
        }
    }
}
