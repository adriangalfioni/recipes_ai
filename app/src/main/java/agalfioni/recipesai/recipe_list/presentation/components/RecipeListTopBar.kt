package agalfioni.recipesai.recipe_list.presentation.components

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.theme.PlusJakartaSans
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navigationIconSize = 40.dp

    TopAppBar(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = null,
                    onClick = onBackClick
                ),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left),
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.outline
            )
        },
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = navigationIconSize), // compensate nav icon
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.recipe_suggestions),
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
    )
}

@Preview
@Composable
private fun RecipeListTopBarPreview() {
    RecipesAITheme {
        RecipeListTopBar(
            onBackClick = {}
        )
    }
}