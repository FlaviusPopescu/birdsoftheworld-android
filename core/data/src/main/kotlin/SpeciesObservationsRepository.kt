package dev.flavius.botw.data

import dev.flavius.botw.core.model.SpeciesObservation
import dev.flavius.botw.core.storage.SpeciesUrlLocalDataSource
import dev.flavius.botw.data.api.BirdApi
import dev.flavius.botw.data.api.request.RecentNearbyObservations
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeciesObservationsRepository @Inject constructor(
    private val birdApi: BirdApi,
    private val speciesUrlLocalDataSource: SpeciesUrlLocalDataSource,
) {
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
                    speciesUrl = "TODO",
                    latitude = it.latitude,
                    longitude = it.longitude,
                    dateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                    isValid = it.isValid,
                    isReviewed = it.isReviewed,
                )
            }
            ?: emptyList()
    }
}
