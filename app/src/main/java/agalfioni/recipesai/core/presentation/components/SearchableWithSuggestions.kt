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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    var isFocused by remember { mutableStateOf(false) }

    val visibleSuggestions = remember(value, suggestions) {
        suggestions
            .filter { it.contains(value, ignoreCase = true) }
            .take(maxSuggestions)
    }
    val showAddNew = value.length >= MIN_CHARS_TO_ADD_NEW_INGREDIENT && visibleSuggestions.isEmpty()

    val hasContentToShow = visibleSuggestions.isNotEmpty() || showAddNew
    LaunchedEffect(hasContentToShow, isFocused) {
        if (isFocused && hasContentToShow) {
            // We need a delay to let the AnimatedVisibility finish
            // its "expand" so the height is fully calculated.
            delay(250)
            bringIntoViewRequester.bringIntoView()
        }
    }

    val handleSelection = remember {
        { text: String ->
            onSelectSuggestion(text)
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    val isSingleResult = visibleSuggestions.size == 1

    Column(
        modifier = modifier
            .bringIntoViewRequester(bringIntoViewRequester)
            .focusTarget()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
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
                placeholder = { Text(placeholder) },
                singleLine = true,
                leadingIcon = leadingIcon?.let { { Icon(it, contentDescription = null) } },
                trailingIcon = if (isSingleResult && trailingIcon != null) {
                    {
                        IconButton(onClick = { handleSelection(visibleSuggestions.first()) }) {
                            Icon(trailingIcon, contentDescription = null)
                        }
                    }
                } else null,
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = if (isSingleResult || showAddNew) ImeAction.Done else ImeAction.Default
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (visibleSuggestions.size == 1) handleSelection(visibleSuggestions.first())
                        if (showAddNew) handleSelection(value.replaceFirstChar { it.titlecase() })
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusEvent { focus ->
                        isFocused = focus.isFocused
                        if (focus.isFocused) {
                            scope.launch {
                                delay(300)
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    }
            )
        }

        AnimatedVisibility(visible = visibleSuggestions.isNotEmpty() || showAddNew) {
            Column {
                if (showAddNew) {
                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { handleSelection(value.replaceFirstChar { it.titlecase() }) }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Add, null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(12.dp))
                        Text(stringResource(R.string.add, value.replaceFirstChar { it.titlecase() }), color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                } else {
                    SuggestionsList(items = visibleSuggestions, onSelect = { handleSelection(it) })
                }
            }
        }
    }
}

const val MIN_CHARS_TO_ADD_NEW_INGREDIENT = 3

@Composable
private fun SuggestionsList(
    items: List<String>,
    onSelect: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .animateContentSize()
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        items.forEach { item ->
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
            value = "asd",
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

@Preview
@Composable
private fun SearchableWithAddOptionPreview() {
    RecipesAITheme {
        SearchableWithSuggestions(
            value = "asd",
            placeholder = stringResource(R.string.add_more_ingredients),
            onValueChange = {},
            suggestions = listOf(),
            onSelectSuggestion = {},
            trailingIcon = Icons.Default.Add,
            onTrailingClick = {},
            onFocusedAtY = null
        )
    }
}