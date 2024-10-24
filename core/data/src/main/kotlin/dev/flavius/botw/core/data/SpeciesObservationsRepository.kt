package dev.flavius.botw.core.data

import dev.flavius.botw.core.model.SpeciesObservation
import dev.flavius.botw.core.model.SpeciesObservation.Companion.buildUrl
import dev.flavius.botw.core.model.SpeciesObservation.Companion.parseDateTime
import dev.flavius.botw.core.storage.SpeciesObservationsLocalDataSource
import dev.flavius.botw.data.network.birds.BirdApi
import dev.flavius.botw.data.network.birds.request.RecentNearbyObservations
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeciesObservationsRepository @Inject constructor(
    private val birdApi: BirdApi,
    private val speciesObservationsLocalDataSource: SpeciesObservationsLocalDataSource,
) {
    suspend fun getNearbyObservations(
        latitude: Float,
        longitude: Float
    ): List<SpeciesObservation> {
        return birdApi
            .get(RecentNearbyObservations(latitude, longitude))
            .getOrNull()
            ?.map { networkModel ->
                SpeciesObservation(
                    totalCount = networkModel.observationCount,
                    commonName = networkModel.commonName,
                    scientificName = networkModel.scientificName,
                    speciesUrl = buildUrl(networkModel.speciesCode),
                    latitude = networkModel.latitude,
                    longitude = networkModel.longitude,
                    dateTime = parseDateTime(networkModel.observationDate),
                    isValid = networkModel.isValid,
                    isReviewed = networkModel.isReviewed,
                )
            }
            ?: speciesObservationsLocalDataSource.getSampleObservations().map { dbModel ->
                SpeciesObservation(
                    totalCount = dbModel.observationCount,
                    commonName = dbModel.commonName,
                    scientificName = dbModel.scientificName,
                    speciesUrl = buildUrl(dbModel.speciesCode),
                    latitude = dbModel.latitude,
                    longitude = dbModel.longitude,
                    dateTime = parseDateTime(dbModel.observationDate),
                    isValid = dbModel.isValid,
                    isReviewed = dbModel.isReviewed,
                )
            }
    }
}
