package dev.flavius.botw.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mapbox.common.MapboxOptions
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import dev.flavius.botw.MainViewModel

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
    onButton: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box {
            MapboxMap(
                Modifier.fillMaxSize(),
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
