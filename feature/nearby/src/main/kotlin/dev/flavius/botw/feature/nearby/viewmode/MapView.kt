package dev.flavius.botw.feature.nearby.viewmode

import android.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.gson.JsonPrimitive
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
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotationGroup
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.extension.style.expressions.dsl.generated.literal
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.plugin.annotation.AnnotationConfig
import com.mapbox.maps.plugin.annotation.AnnotationSourceOptions
import com.mapbox.maps.plugin.annotation.ClusterOptions
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import dev.flavius.botw.core.model.SpeciesObservation
import dev.flavius.botw.feature.nearby.item.SpeciesObservation
import dev.flavius.botw.main.R
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun MapView(
    speciesObservations: List<SpeciesObservation>,
    selectedSpeciesObservation: SpeciesObservation?,
    onItemSelected: (SpeciesObservation?) -> Unit,
    modifier: Modifier = Modifier,
    onLearnMore: (String) -> Unit,
) {
    val observationsByUuid = remember(speciesObservations) {
        speciesObservations.associateBy { it.uuid.toHexString() }
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
    val visitedObservations = remember(speciesObservations) { mutableSetOf<SpeciesObservation>() }
    selectedSpeciesObservation?.let { visitedObservations.add(it) }
    MapboxMap(
        modifier
            .fillMaxSize()
            .onSizeChanged {
                mapWidth = it.width
            },
        scaleBar = {},
        compass = {
            Compass(alignment = Alignment.BottomEnd)
        },
        onMapClickListener = {
            onItemSelected(null)
            false
        },
        mapViewportState = mapViewportState,
    ) {
        MapEffect(speciesObservations) { mapView ->
            if (speciesObservations.isEmpty()) return@MapEffect
            val coordinates = speciesObservations.map {
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
        MapEffect(selectedSpeciesObservation) {
            selectedSpeciesObservation?.let {
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
        val icon = rememberIconImage(Unit, painterResource(R.drawable.pin_new))
        PointAnnotationGroup(
            annotations = speciesObservations.map {
                PointAnnotationOptions()
                    .withPoint(
                        Point.fromLngLat(it.longitude.toDouble(), it.latitude.toDouble())
                    )
                    .withIconOpacity(if (it in visitedObservations) 0.5 else 1.0)
                    .withData(JsonPrimitive(it.uuid.toHexString()))
            },
            annotationConfig = AnnotationConfig(
                annotationSourceOptions = AnnotationSourceOptions(
                    clusterOptions = ClusterOptions(
                        clusterRadius = 1,
                        textColorExpression = Expression.color(Color.WHITE),
                        textColor = Color.BLACK,
                        textSize = 14.0,
                        circleRadiusExpression = literal(18.0),
                        colorLevels = listOf(
                            Pair(0, 0xFF365515.toInt()),
                        )
                    )
                )
            ),
        ) {
            iconImage = icon
            interactionsState.onClicked {
                observationsByUuid[it.getData()?.asJsonPrimitive?.asString]
                    ?.let { speciesObservation ->
                        onItemSelected(speciesObservation)
                        visitedObservations.add(speciesObservation)
                    }
                true
            }
        }
        selectedSpeciesObservation?.let {
            ViewAnnotation(
                modifier = Modifier
                    .width(mapWidth.dp),
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
                SpeciesObservation(
                    it,
                    modifier = Modifier.fillMaxWidth(),
                    widthFraction = 0.7f
                ) { onLearnMore(it.speciesUrl) }
            }
        }
    }
}
