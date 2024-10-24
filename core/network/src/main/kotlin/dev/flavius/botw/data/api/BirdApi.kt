package dev.flavius.botw.data.api

import dev.flavius.botw.data.api.request.RecentNearbyObservations
import dev.flavius.botw.data.api.response.SpeciesObservation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import javax.inject.Inject

class BirdApi @Inject constructor(private val httpClient: HttpClient) {
    suspend fun get(
        recentNearbyObservations: RecentNearbyObservations
    ): Result<List<SpeciesObservation>> =
        runCatching {
            httpClient.get(recentNearbyObservations).body<List<SpeciesObservation>>()
        }

    object Endpoints {
        const val BASE_URL = "https://api.ebird.org/v2/"

        object Observations {
            const val RECENT_NEARBY = "/data/obs/geo/recent"
        }
    }
}
