package dev.flavius.botw.data.network.birds

import dev.flavius.botw.data.network.birds.BirdApi.Endpoints.API_HOST
import dev.flavius.botw.data.network.birds.BirdApi.Endpoints.Observations.RECENT_NEARBY
import dev.flavius.botw.data.network.birds.config.birdsApiConfig
import dev.flavius.botw.data.network.birds.request.RecentNearbyObservations
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.util.flattenForEach
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BirdApiTest {
    private val sampleResponse = this::class.java.getResourceAsStream("/nearby_observations.json")!!
        .use { inputStream -> inputStream.bufferedReader().use { it.readText() } }

    @Test
    fun `calls the recent observations endpoint`() {
        val nearbyRequest = RecentNearbyObservations(
            latitude = -6.0946436f,
            longitude = 146.8908f,
            daysBehind = 28,
            radiusKm = 100,
        )
        runBlocking {
            BirdApi(
                HttpClient(
                    MockEngine { httpRequest ->
                        assertTrue { httpRequest.url.toString().contains(API_HOST) }
                        assertTrue { httpRequest.url.toString().contains(RECENT_NEARBY) }
                        httpRequest.url.parameters.flattenForEach { name, value ->
                            when (name) {
                                "lat" -> {
                                    assertEquals(nearbyRequest.latitude.toString(), value)
                                }
                                "lng" -> {
                                    assertEquals(nearbyRequest.longitude.toString(), value)
                                }
                                "back" -> {
                                    assertEquals(nearbyRequest.daysBehind.toString(), value)
                                }
                                "dist" -> {
                                    assertEquals(nearbyRequest.radiusKm.toString(), value)
                                }
                                else -> throw IllegalArgumentException(
                                    "Additional unexpected query parameter: $name"
                                )
                            }
                        }

                        respond(
                            content = sampleResponse,
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                        )
                    }
                ) {
                    birdsApiConfig(Logger.DEFAULT, "token")
                }
            ).get(nearbyRequest).run {
                assertEquals(true, isSuccess)
                getOrThrow().let { observations ->
                    assertTrue { observations.isNotEmpty() }
                    observations.forEach {
                        it.run {
                            // for CSV import
                            println(
                                "\"$speciesCode\",\"$commonName\",\"$scientificName\"," +
                                        "\"$locationId\",\"" +
                                        "$locationName\",\"$observationDate\"," +
                                        "\"$observationCount\",\"$latitude\",\"" +
                                        "$longitude\",\"$isValid\",\"$isReviewed\"," +
                                        "\"$isLocationPrivate\",\"" +
                                        "$subregionId\""
                            )
                        }
                    }
                }
            }
        }
    }
}
