package dev.flavius.botw.core.data.extensions

import dev.flavius.botw.core.model.SpeciesObservation
import dev.flavius.botw.core.model.SpeciesObservation.Companion.buildUrl
import dev.flavius.botw.core.model.SpeciesObservation.Companion.parseDateTime
import dev.flavius.botw.data.network.birds.response.SpeciesObservation as NetworkSpeciesObservation

fun NetworkSpeciesObservation.toDomainModel() = SpeciesObservation(
    totalCount = observationCount,
    commonName = commonName,
    scientificName = scientificName,
    speciesUrl = buildUrl(speciesCode),
    latitude = latitude,
    longitude = longitude,
    dateTime = parseDateTime(observationDate),
    isValid = isValid,
    isReviewed = isReviewed,
)
