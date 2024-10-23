package dev.flavius.botw.data

import dev.flavius.botw.core.model.SpeciesObservation
import dev.flavius.botw.data.api.BirdApi
import javax.inject.Inject

class SpeciesObservationsRepository @Inject constructor(
    private val birdApi: BirdApi,
) {
    suspend fun getNearbyObservations(
        latitude: Float,
        longitude: Float
    ): Result<List<SpeciesObservation>> {
        return Result.failure(UnsupportedOperationException("unavailable"))
    }
}
