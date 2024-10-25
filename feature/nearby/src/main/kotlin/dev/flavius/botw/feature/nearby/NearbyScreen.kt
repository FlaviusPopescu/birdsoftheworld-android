package dev.flavius.botw.feature.nearby

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.flavius.botw.core.model.SpeciesObservation
import dev.flavius.botw.feature.nearby.search.SearchBar
import dev.flavius.botw.feature.nearby.viewmode.ListView
import dev.flavius.botw.feature.nearby.viewmode.MapView
import dev.flavius.botw.main.R

@Composable
fun NearbyScreen(
    nearbyViewModel: NearbyViewModel,
    modifier: Modifier = Modifier,
) {
    val placeSuggestions by nearbyViewModel.placeSuggestions.collectAsState()
    val speciesObservations by nearbyViewModel.nearbyObservations.collectAsState()
    var viewMode by remember { mutableStateOf(ViewMode.Map) }

    Surface(modifier = modifier.fillMaxSize()) {
        Column {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                autoSuggestOptions = placeSuggestions,
                onTextSteady = { nearbyViewModel.searchPlacesByQuery(it) },
                onPlaceSelected = { nearbyViewModel.searchObservationsByPlace(it) }
            )
            Box {
                val onLearnMore = { url: String ->
                    // todo
                }
                var selectedSpeciesObservation by remember {
                    mutableStateOf<SpeciesObservation?>(
                        null
                    )
                }
                MapView(
                    speciesObservations,
                    selectedSpeciesObservation = selectedSpeciesObservation,
                    onItemSelected = { selectedSpeciesObservation = it },
                    onLearnMore = onLearnMore,
                )
                if (viewMode == ViewMode.List) {
                    ListView(
                        speciesObservations,
                        onLearnMore = onLearnMore,
                        onItemSelected = {
                            selectedSpeciesObservation = it
                            viewMode = ViewMode.Map
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                ) {
                    ExtendedFloatingActionButton(
                        onClick = { viewMode = viewMode.toggle() },
                        shape = MaterialTheme.shapes.extraLarge,
                        modifier = Modifier.align(Alignment.TopCenter)
                    ) {
                        Icon(
                            painterResource(viewMode.iconDrawableRes),
                            stringResource(viewMode.contentDescriptionRes)
                        )
                        Spacer(modifier.width(8.dp))
                        Text(
                            stringResource(viewMode.textStringRes),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                }
            }
        }
    }
}

enum class ViewMode(
    @DrawableRes val iconDrawableRes: Int,
    @StringRes val textStringRes: Int,
    @StringRes val contentDescriptionRes: Int,
) {
    Map(
        R.drawable.list,
        R.string.view_button_list,
        R.string.view_button_list_content_description,
    ),
    List(
        R.drawable.map,
        R.string.view_button_map,
        R.string.view_button_map_content_description,
    );

    fun toggle() = ViewMode.entries[(ordinal + 1) % ViewMode.entries.size]
}
