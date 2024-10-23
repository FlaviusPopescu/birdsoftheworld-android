package dev.flavius.botw.core.model

import kotlinx.datetime.LocalDateTime

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
)
