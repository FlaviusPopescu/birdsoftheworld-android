package dev.flavius.botw.core.data.extensions

import dev.flavius.botw.core.model.SpeciesObservation
import dev.flavius.botw.core.model.SpeciesObservation.Companion.buildUrl
import dev.flavius.botw.core.model.SpeciesObservation.Companion.parseDateTime
import dev.flavius.botw.core.storage.entities.SpeciesObservation as DatabaseSpeciesObservation

@kotlin.uuid.ExperimentalUuidApi
fun DatabaseSpeciesObservation.toDomainModel() = SpeciesObservation(
    totalCount = observationCount,
    commonName = commonName,
    scientificName = scientificName,
    speciesUrl = buildUrl(speciesCode),
    latitude = latitude,
    longitude = longitude,
    approximateEpochSeconds = parseDateTime(observationDate),
    isValid = isValid,
    isReviewed = isReviewed,
)
