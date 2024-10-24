package dev.flavius.botw.data.network.birds

import dev.flavius.botw.core.di.httpclient.BirdApi
import dev.flavius.botw.data.network.birds.request.RecentNearbyObservations
import dev.flavius.botw.data.network.birds.response.SpeciesObservation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import javax.inject.Inject

class BirdApi @Inject constructor(
    @BirdApi private val httpClient: HttpClient
) {
    suspend fun get(
        recentNearbyObservations: RecentNearbyObservations
    ): Result<List<SpeciesObservation>> =
        runCatching {
            httpClient.get(recentNearbyObservations).body<List<SpeciesObservation>>()
        }

    object Endpoints {
        const val API_HOST = "api.ebird.org"

        object Observations {
            const val RECENT_NEARBY = "/v2/data/obs/geo/recent"
        }
    }
}
