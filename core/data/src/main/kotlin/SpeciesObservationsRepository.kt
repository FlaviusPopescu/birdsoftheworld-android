package dev.flavius.botw.data

import dev.flavius.botw.core.model.SpeciesObservation
import dev.flavius.botw.core.model.SpeciesObservation.Companion.buildUrl
import dev.flavius.botw.core.model.SpeciesObservation.Companion.parseDateTime
import dev.flavius.botw.core.storage.SpeciesObservationsLocalDataSource
import dev.flavius.botw.data.api.BirdApi
import dev.flavius.botw.data.api.request.RecentNearbyObservations
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeciesObservationsRepository @Inject constructor(
    private val birdApi: BirdApi,
    private val speciesObservationsLocalDataSource: SpeciesObservationsLocalDataSource,
) {
    suspend fun getTotalSpecies() = speciesObservationsLocalDataSource.totalSpecies()

    suspend fun getNearbyObservations(
        latitude: Float,
        longitude: Float
    ): List<SpeciesObservation> {
        return birdApi
            .get(RecentNearbyObservations(latitude, longitude))
            .getOrNull()
            ?.map {
                SpeciesObservation(
                    totalCount = it.observationCount,
                    commonName = it.commonName,
                    scientificName = it.scientificName,
                    speciesUrl = buildUrl(it.speciesCode),
                    latitude = it.latitude,
                    longitude = it.longitude,
                    dateTime = parseDateTime(it.observationDate),
                    isValid = it.isValid,
                    isReviewed = it.isReviewed,
                )
            }
            ?: emptyList()
    }
}
