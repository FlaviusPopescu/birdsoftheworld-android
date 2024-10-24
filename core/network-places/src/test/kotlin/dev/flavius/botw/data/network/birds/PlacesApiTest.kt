package dev.flavius.botw.data.network.birds

import dev.flavius.botw.data.network.places.PlacesApi
import dev.flavius.botw.data.network.places.PlacesApi.Endpoints.API_HOST
import dev.flavius.botw.data.network.places.PlacesApi.Endpoints.Geocode.FORWARD
import dev.flavius.botw.data.network.places.config.placesApiConfig
import dev.flavius.botw.data.network.places.request.PlacesQuery
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

class PlacesApiTest {
    private val sampleResponse = this::class.java
        .getResourceAsStream("/forward_geocoding_response.json")!!
        .use { inputStream -> inputStream.bufferedReader().use { it.readText() } }

    @Test
    fun `calls the recent observations endpoint`() {
        val placesQuery = PlacesQuery("national mall")
        runBlocking {
            PlacesApi(
                HttpClient(
                    MockEngine { httpRequest ->
                        assertTrue { httpRequest.url.toString().contains(API_HOST) }
                        assertTrue { httpRequest.url.toString().contains(FORWARD) }
                        assertTrue { "access_token" in httpRequest.url.parameters.names() }
                        assertTrue { "q" in httpRequest.url.parameters.names() }
                        httpRequest.url.parameters.flattenForEach { name, value ->
                            when (name) {
                                "q" -> {
                                    assertEquals(placesQuery.query, value)
                                }
                                "access_token" -> {
                                    assertTrue { value.isNotEmpty() }
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
                    placesApiConfig(Logger.DEFAULT, "token")
                }
            ).get(placesQuery).run {
                assertEquals(true, isSuccess)
                getOrThrow().let { features ->
                    assertTrue { features.isNotEmpty() }
                    features.forEach { feature ->
                        println(
                            "${feature.properties.displayName} -> " +
                                    "[${feature.properties.fullAddress}] " +
                                    "${feature.properties.coordinates}"
                        )
                    }
                }
            }
        }
    }
}
