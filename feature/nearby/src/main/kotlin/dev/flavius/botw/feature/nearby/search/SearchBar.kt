package dev.flavius.botw.feature.nearby.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.flavius.botw.core.model.PlaceSuggestion
import dev.flavius.botw.main.R
import kotlinx.coroutines.delay

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    autoSuggestOptions: List<PlaceSuggestion>,
    onTextSteady: (String) -> Unit,
    onPlaceSelected: (PlaceSuggestion) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    var expanded by remember(autoSuggestOptions) { mutableStateOf(autoSuggestOptions.isNotEmpty()) }
    var shouldSearch by remember { mutableStateOf(true) }

    Surface(modifier = modifier.padding(top = 40.dp), shape = MaterialTheme.shapes.medium) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val focusRequester = remember { FocusRequester() }
            val focusManager = LocalFocusManager.current
            OutlinedTextField(
                text,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                onValueChange = { text = it },
                placeholder = { Text(stringResource(R.string.search_hint)) },
                colors = with(remember { Color(0xFFEFEFEC) }) {
                    OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = this,
                        focusedBorderColor = this,
                        focusedContainerColor = this,
                        unfocusedContainerColor = this,
                    )
                },
                shape = MaterialTheme.shapes.extraLarge,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        stringResource(R.string.search_content_description)
                    )
                },
                trailingIcon = {
                    AnimatedVisibility(text.isNotEmpty()) {
                        IconButton(onClick = { text = "" }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                stringResource(R.string.search_clear_content_description)
                            )
                        }
                    }
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White).fillMaxWidth()
            ) {
                autoSuggestOptions.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                it.address,
                                style = MaterialTheme.typography.bodyLarge,
                                fontSize = 18.sp
                            )
                        },
                        onClick = {
                            text = it.address
                            expanded = false
                            shouldSearch = false
                            focusManager.clearFocus()
                            onPlaceSelected(it)
                        })
                }
            }
        }
    }
    LaunchedEffect(text) {
        if (!shouldSearch) {
            shouldSearch = true
            return@LaunchedEffect
        }
        delay(800L)
        onTextSteady(text)
    }
}
