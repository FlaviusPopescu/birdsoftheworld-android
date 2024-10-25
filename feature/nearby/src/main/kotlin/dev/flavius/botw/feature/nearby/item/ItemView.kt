package dev.flavius.botw.feature.nearby.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import dev.flavius.botw.core.model.SpeciesObservation
import dev.flavius.botw.main.R
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

@Composable
fun SpeciesObservation(
    speciesObservation: SpeciesObservation,
    modifier: Modifier = Modifier,
    widthFraction: Float = 1f,
    actionButton: @Composable () -> Unit = {},
    onObservationSelected: (SpeciesObservation) -> Unit,
) {
    Box(modifier = modifier) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(widthFraction)
                .align(Alignment.Center),
            shape = MaterialTheme.shapes.small
        ) {
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
                                painterResource(R.drawable.verified),
                                stringResource(R.string.observation_reviewed),
                                tint = Color(0xFF83E07F)
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
                        if (speciesObservation.totalCount > 0) {
                            Text(
                                pluralStringResource(
                                    R.plurals.observation_count,
                                    speciesObservation.totalCount,
                                    speciesObservation.totalCount,
                                ),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                        speciesObservation.approximateEpochSeconds?.let {
                            Text(
                                Instant.fromEpochSeconds(it)
                                    .toLocalDateTime(TimeZone.currentSystemDefault())
                                    .format(dateTimeFormat)
                            )
                        }
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
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        actionButton()
                    }
                }
            }
        }

    }
}

private val dateTimeFormat = LocalDateTime.Format {
    dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
    chars(", ")
    monthName(MonthNames.ENGLISH_ABBREVIATED)
    char(' ')
    dayOfMonth()
    chars(", ")
    year()
}
