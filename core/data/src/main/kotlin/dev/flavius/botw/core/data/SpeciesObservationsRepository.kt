package dev.flavius.botw.core.data

import dev.flavius.botw.core.data.extensions.toDomainModel
import dev.flavius.botw.core.model.SpeciesObservation
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
                networkModel.toDomainModel()
            }
            ?: speciesObservationsLocalDataSource
                .getSampleObservations()
                .map { dbModel ->
                    dbModel.toDomainModel()
                }
    }

    suspend fun getSampleObservations() = speciesObservationsLocalDataSource
        .getSampleObservations()
        .map { it.toDomainModel() }
}
