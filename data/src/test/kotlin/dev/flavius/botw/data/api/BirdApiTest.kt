package dev.flavius.botw.data.api

import dev.flavius.botw.data.api.BirdApi.Endpoints.BASE_URL
import dev.flavius.botw.data.api.BirdApi.Endpoints.Observations
import dev.flavius.botw.data.api.request.RecentNearbyObservations
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import io.ktor.util.flattenForEach
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class BirdApiTest {
    private val sampleResponse = this::class.java.getResourceAsStream("/nearby_observations.json")!!
        .use { inputStream -> inputStream.bufferedReader().use { it.readText() } }

    @Test
    fun `calls the recent observations endpoint`() {
        val recentNearbyObservations = RecentNearbyObservations(
            latitude = -6.0946436f,
            longitude = 146.8908f,
            daysBehind = 28,
            radiusKm = 100,
        )
        runBlocking {
            BirdApi(
                engine = MockEngine { request ->
                    assertTrue { request.url.toString().startsWith(BASE_URL) }
                    assertTrue { request.url.fullPath.contains(Observations.RECENT_NEARBY) }
                    request.url.parameters.flattenForEach { name, value ->
                        when (name) {
                            "lat" -> {
                                assertEquals(recentNearbyObservations.latitude.toString(), value)
                            }
                            "lng" -> {
                                assertEquals(recentNearbyObservations.longitude.toString(), value)
                            }
                            "back" -> {
                                assertEquals(recentNearbyObservations.daysBehind.toString(), value)
                            }
                            "dist" -> {
                                assertEquals(recentNearbyObservations.radiusKm.toString(), value)
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
            ).get(recentNearbyObservations).run {
                assertEquals(true, isSuccess)
                getOrThrow().let { observations ->
                    assertTrue { observations.isNotEmpty() }
                }
            }
        }
    }
}
