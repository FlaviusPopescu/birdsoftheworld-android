package dev.flavius.botw.core.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char

data class SpeciesObservation(
    val totalCount: Int,
    val commonName: String,
    val scientificName: String,
    val speciesUrl: String,
    val latitude: Float,
    val longitude: Float,
    val dateTime: LocalDateTime,
    val isValid: Boolean,
    val isReviewed: Boolean,
) {
    companion object {
        fun buildUrl(speciesCode: String) =
            "https://birdsoftheworld.org/bow/species/$speciesCode/cur/introduction"

        fun parseDateTime(date: String) = LocalDateTime.parse(date, dateTimeFormat)

        private val dateTimeFormat = LocalDateTime.Format {
            date(LocalDate.Formats.ISO)
            char(' ')
            hour(); char(':'); minute()
        }
    }
}
