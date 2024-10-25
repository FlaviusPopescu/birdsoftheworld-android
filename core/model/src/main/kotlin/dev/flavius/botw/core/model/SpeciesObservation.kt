package dev.flavius.botw.core.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone.Companion.UTC
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class SpeciesObservation(
    val uuid: Uuid = Uuid.random(),
    val totalCount: Int,
    val commonName: String,
    val scientificName: String,
    val speciesUrl: String,
    val latitude: Float,
    val longitude: Float,
    val approximateEpochSeconds: Long? = 0L,
    val isValid: Boolean,
    val isReviewed: Boolean,
) {
    companion object {
        fun buildUrl(speciesCode: String) =
            "https://birdsoftheworld.org/bow/species/$speciesCode/cur/introduction"

        fun parseDateTime(date: String): Long? = runCatching {
            LocalDateTime.parse(date, dateTimeFormat)
        }.getOrNull()?.toInstant(UTC)?.epochSeconds ?: runCatching {
            LocalDateTime.parse(date, dateFormat)
        }.getOrNull()?.toInstant(UTC)?.epochSeconds

        private val dateTimeFormat = LocalDateTime.Format {
            date(LocalDate.Formats.ISO)
            char(' ')
            hour(); char(':'); minute()
        }

        private val dateFormat = LocalDateTime.Format {
            date(LocalDate.Formats.ISO)
        }
    }
}
