package agalfioni.recipesai.recipe.presentation.recipe_list.components

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import agalfioni.recipesai.recipe.presentation.recipe_list.AiProgressState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AiProgressSection(
    aiProgressUiState: AiProgressState,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.generating_your_menu),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = stringResource(R.string.finding_perfect_recipe),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(36.dp))
        AiProgressBar(
            progress = aiProgressUiState.progress,
            message = aiProgressUiState.steps.getOrNull(aiProgressUiState.stepIndex)?.message?.asString().orEmpty()
        )
    }

}

@Preview
@Composable
private fun AiProgressSectionPreview() {
    RecipesAITheme {
        AiProgressSection(
            aiProgressUiState = AiProgressState()
        )
    }
}