package agalfioni.recipesai.core.presentation.components

import agalfioni.recipesai.R
import agalfioni.recipesai.core.presentation.theme.RecipesAITheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchableWithSuggestions(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    suggestions: List<String>,
    onSelectSuggestion: (String) -> Unit,
    modifier: Modifier = Modifier,
    onFocusedAtY: ((Float) -> Unit)? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingClick: ((String) -> Unit)? = null,
    maxSuggestions: Int = 5,
) {

    var yInRoot by remember { mutableFloatStateOf(0f) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val visibleSuggestions = remember(value, suggestions) {
        suggestions
            .filter { it.contains(value, ignoreCase = true) }
            .take(maxSuggestions)
    }

    val isSingleResult = visibleSuggestions.size == 1

    Column(
        modifier
            .heightIn(max = 500.dp)
            .focusTarget() // Allows the Column to receive focus
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // Removes the ripple effect
            ) {
                // Tapping outside will now naturally clear focus
                focusManager.clearFocus()
                keyboardController?.hide()
            }
    ) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            tonalElevation = 2.dp,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = {
                    Text(placeholder)
                },
                singleLine = true,
                leadingIcon = leadingIcon?.let {
                    { Icon(it, contentDescription = null) }
                },
                trailingIcon = if (isSingleResult && trailingIcon != null) {
                    {
                        IconButton(onClick = {
                            onSelectSuggestion(visibleSuggestions.first())
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }) {
                            Icon(trailingIcon, contentDescription = null)
                        }
                    }
                } else null,
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = if (isSingleResult) ImeAction.Done else ImeAction.Default
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (visibleSuggestions.size == 1) {
                            onSelectSuggestion(visibleSuggestions.first())
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (onFocusedAtY != null) {
                            Modifier
                                .onGloballyPositioned { coordinates ->
                                    yInRoot = coordinates.positionInRoot().y
                                }
                                .onFocusEvent { focus ->
                                    if (focus.isFocused) {
                                        onFocusedAtY(yInRoot)
                                    }
                                }
                        } else {
                            Modifier
                        }
                    )

            )
        }

        // SUGGESTIONS
        AnimatedVisibility(
            visible = visibleSuggestions.isNotEmpty()
        ) {
            SuggestionsList(
                items = visibleSuggestions,
                onSelect = {
                    onSelectSuggestion(it)
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            )
        }
    }
}

@Composable
private fun SuggestionsList(
    items: List<String>,
    onSelect: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .animateContentSize()
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        items(
            items = items,
            key = { it }
        ) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(item) }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchableWithSuggestionsPreview() {
    RecipesAITheme {
        SearchableWithSuggestions(
            value = "",
            placeholder = stringResource(R.string.add_more_ingredients),
            onValueChange = {},
            suggestions = listOf(
                "Apple", "Carrot", "Milk", "Cheese"
            ),
            onSelectSuggestion = {},
            trailingIcon = Icons.Default.Add,
            onTrailingClick = {},
            onFocusedAtY = null
        )
    }
}