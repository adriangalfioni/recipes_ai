package agalfioni.recipesai.recipe.presentation.recipedetails.components

import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.recipe.presentation.recipedetails.models.Instruction
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InstructionItem(
    instruction: Instruction,
    isLast: Boolean = false,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.Top,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(if (instruction.step == 1) Color(0xFFB2F2BB) else Color(0xFFF1F3F1)),
                contentAlignment = Alignment.Center,
            ) {
                Text(instruction.step.toString(), fontWeight = FontWeight.Bold)
            }
            if (!isLast) {
                VerticalDivider(
                    thickness = 1.dp,
                    color = Color.Gray,
                )
            }
        }
        Spacer(Modifier.width(16.dp))
        Column {
            Text(instruction.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(instruction.description, color = Color.Gray, lineHeight = 20.sp)
            Spacer(Modifier.height(14.dp))
        }
    }
}

@Preview
@Composable
private fun InstructionItemPreview() {
    RecipesAITheme {
        InstructionItem(
            Instruction(
                2,
                "Prepare the potatoes",
                "Cut potatoes into 2-inch chunks and chicken breasts into similar size pieces if desired, or leave whole.",
            ),
        )
    }
}
