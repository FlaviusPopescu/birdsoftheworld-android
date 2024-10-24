package dev.flavius.botw.feature.nearby

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.coroutine.awaitCameraForCoordinates
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import dev.flavius.botw.core.model.PlaceSuggestion
import dev.flavius.botw.core.model.SpeciesObservation
import dev.flavius.botw.main.R
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char

@Composable
fun NearbyScreen(
    nearbyViewModel: NearbyViewModel,
    modifier: Modifier = Modifier,
    onButton: () -> Unit,
) {
    val placeSuggestions by nearbyViewModel.placeSuggestions.collectAsState()
    val speciesObservations by nearbyViewModel.nearbyObservations.collectAsState()
    var selectedObservation by remember {
        mutableStateOf<SpeciesObservation?>(null)
    }
    var selectedObservationInfo by remember {
        mutableStateOf<SpeciesObservation?>(null)
    }
    var mapWidth by remember { mutableIntStateOf(0) }

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(2.0)
            center(Point.fromLngLat(-98.0, 39.5))
            pitch(0.0)
            bearing(0.0)
        }
    }

    LaunchedEffect(speciesObservations) {
        println("#species total=${speciesObservations.size} $speciesObservations")
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
                    Modifier
                        .fillMaxSize()
                        .onSizeChanged {
                            mapWidth = it.width
                        },
                    scaleBar = {},
                    compass = {
                        Compass(alignment = Alignment.BottomEnd)
                    },
                    onMapClickListener = {
                        selectedObservation = null
                        selectedObservationInfo = null
                        false
                    },
                    mapViewportState = mapViewportState,
                ) {
                    val maxObs = 100
                    MapEffect(speciesObservations) { mapView ->
                        selectedObservation = null
                        selectedObservationInfo = null

                        if (speciesObservations.isEmpty()) return@MapEffect
                        val coordinates = speciesObservations.take(maxObs).map {
                            Point.fromLngLat(it.longitude.toDouble(), it.latitude.toDouble())
                        }
                        val polygon = Polygon.fromLngLats(listOf(coordinates))
                        val cameraPosition = mapView.mapboxMap.awaitCameraForCoordinates(
                            polygon.coordinates().flatten(),
                            cameraOptions { },
                            EdgeInsets(1.0, 1.0, 1.0, 1.0)
                        )
                        mapViewportState.setCameraOptions(cameraPosition)
                    }
                    MapEffect(selectedObservation) {
                        selectedObservation?.let {
                            mapViewportState.easeTo(
                                cameraOptions {
                                    center(
                                        Point.fromLngLat(
                                            it.longitude.toDouble(),
                                            it.latitude.toDouble(),
                                        )
                                    )
                                },
                            )
                        }
                    }
                    val marker = rememberIconImage(
                        key = "key", painter = painterResource(R.drawable.pin_deselected)
                    )

                    speciesObservations.take(maxObs).forEach { speciesObservation ->
                        val observationPoint = Point.fromLngLat(
                            speciesObservation.longitude.toDouble(),
                            speciesObservation.latitude.toDouble()
                        )
                        PointAnnotation(
                            observationPoint,
                            onClick = {
                                selectedObservationInfo = speciesObservation
                                selectedObservation = speciesObservation
                                false
                            }
                        ) {
                            iconImage = marker
                        }
                    }

                    selectedObservationInfo?.let {
                        ViewAnnotation(
                            modifier = Modifier
                                .width(mapWidth.dp)
                                .align(Alignment.Center),
                            options = viewAnnotationOptions {
                                annotationAnchor {
                                    anchor(ViewAnnotationAnchor.CENTER)
                                }
                                geometry(
                                    Point.fromLngLat(
                                        it.longitude.toDouble(),
                                        it.latitude.toDouble()
                                    )
                                )
                            }
                        ) {
                            SpeciesObservation(it) {
                                // todo open URL
                            }
                        }
                    }
                }
            }
        }
    }
}

val dateTimeFormat = LocalDateTime.Format {
    dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
    chars(", ")
    monthName(MonthNames.ENGLISH_FULL)
    char(' ')
    dayOfMonth()
    chars(", ")
    year()
}

@Composable
fun SpeciesObservation(
    speciesObservation: SpeciesObservation,
    modifier: Modifier = Modifier,
    onObservationSelected: (SpeciesObservation) -> Unit,
) {
    Surface(modifier = modifier, shape = MaterialTheme.shapes.small) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (speciesObservation.isReviewed) {
                        Icon(
                            Icons.Filled.Check,
                            stringResource(R.string.observation_reviewed),
                            tint = Color.Green
                        )
                    }
                    if (speciesObservation.isValid.not()) {
                        Icon(
                            Icons.Filled.Warning,
                            stringResource(R.string.observation_invalid),
                            tint = MaterialTheme.colorScheme.error,
                        )
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        speciesObservation.commonName,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "(${speciesObservation.scientificName})",
                        style = MaterialTheme.typography.titleSmall,
                        fontStyle = FontStyle.Italic,
                        color = Color.DarkGray
                    )
                    Text(
                        pluralStringResource(
                            R.plurals.observation_count,
                            speciesObservation.totalCount,
                            speciesObservation.totalCount,
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(speciesObservation.dateTime.format(dateTimeFormat))
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Filled.Info,
                    stringResource(R.string.observation_info),
                    tint = MaterialTheme.colorScheme.primary,
                )
                TextButton(onClick = { onObservationSelected(speciesObservation) }) {
                    Text(stringResource(R.string.observation_button_information))
                }
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
                modifier = Modifier.background(Color.White)
            ) {
                autoSuggestOptions.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(it.address, style = MaterialTheme.typography.labelLarge)
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
        delay(1_800L)
        onTextSteady(text)
    }
}
