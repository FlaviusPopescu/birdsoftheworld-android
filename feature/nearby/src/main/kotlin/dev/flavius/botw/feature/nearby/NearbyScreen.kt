package dev.flavius.botw.feature.nearby

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import dev.flavius.botw.core.model.PlaceSuggestion
import dev.flavius.botw.main.R
import kotlinx.coroutines.delay

@Composable
fun NearbyScreen(
    nearbyViewModel: NearbyViewModel,
    modifier: Modifier = Modifier,
    onButton: () -> Unit,
) {
    val placeSuggestions by nearbyViewModel.placeSuggestions.collectAsState()
    val speciesObservations by nearbyViewModel.nearbyObservations.collectAsState()

    LaunchedEffect(speciesObservations) {
        println("#species $speciesObservations")
    }

    Surface(modifier = modifier.fillMaxSize()) {
        Column {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                autoSuggestOptions = placeSuggestions,
                onTextSteady = { nearbyViewModel.searchPlacesByQuery(it) },
                onPlaceSelected = { nearbyViewModel.searchObservationsByPlace(it) }
            )
            Box {
                MapboxMap(
                    Modifier.fillMaxSize(),
                    scaleBar = {},
                    compass = {
                        Compass(alignment = Alignment.BottomEnd)
                    },
                    mapViewportState = rememberMapViewportState {
                        setCameraOptions {
                            zoom(2.0)
                            center(Point.fromLngLat(-98.0, 39.5))
                            pitch(0.0)
                            bearing(0.0)
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    autoSuggestOptions: List<PlaceSuggestion>,
    onTextSteady: (String) -> Unit,
    onPlaceSelected: (PlaceSuggestion) -> Unit,
) {
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
//    var size by remember { mutableStateOf(Size.Zero) }

    Surface(modifier = modifier.padding(top = 32.dp)) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            OutlinedTextField(
                text,
                modifier = Modifier
                    .fillMaxWidth(),
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
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                autoSuggestOptions.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(it.address)
                        },
                        onClick = {
                            expanded = false
                            onPlaceSelected(it)
                        })
                }
            }
        }
    }
    LaunchedEffect(text) {
        delay(500L)
        onTextSteady(text)
        expanded = true
    }
}
