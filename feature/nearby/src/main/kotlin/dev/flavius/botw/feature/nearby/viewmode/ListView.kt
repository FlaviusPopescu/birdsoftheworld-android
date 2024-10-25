package dev.flavius.botw.feature.nearby.viewmode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.flavius.botw.core.model.SpeciesObservation
import dev.flavius.botw.feature.nearby.item.SpeciesObservation
import dev.flavius.botw.main.R

@Composable
fun ListView(
    speciesObservations: List<SpeciesObservation>,
    modifier: Modifier = Modifier,
    onLearnMore: (String) -> Unit,
    onItemSelected: (SpeciesObservation) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEC))
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        items(speciesObservations) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                SpeciesObservation(
                    item,
                    actionButton = {
                        TextButton(onClick = { onItemSelected(item) }) {
                            Text(stringResource(R.string.item_see_on_map))
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) { onLearnMore(it.speciesUrl) }
            }
        }
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
