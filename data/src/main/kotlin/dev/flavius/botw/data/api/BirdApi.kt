package dev.flavius.botw.data.api

import dev.flavius.botw.data.api.BirdApi.Endpoints.BASE_URL
import dev.flavius.botw.data.api.request.RecentNearbyObservations
import dev.flavius.botw.data.api.response.SpeciesObservation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.resources.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.serialization
import kotlinx.serialization.json.Json
import javax.inject.Inject

class BirdApi @Inject constructor(engine: HttpClientEngine) {
    private val httpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            serialization(ContentType.Application.Json, Json { ignoreUnknownKeys = true })
        }
        install(Resources)
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        defaultRequest {
            url(BASE_URL)
        }
    }

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