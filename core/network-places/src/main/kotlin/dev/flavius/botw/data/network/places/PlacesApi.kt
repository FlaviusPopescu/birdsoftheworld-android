package dev.flavius.botw.data.network.places

import dev.flavius.botw.core.di.httpclient.PlacesApi
import dev.flavius.botw.data.network.places.request.PlacesQuery
import dev.flavius.botw.data.network.places.response.Feature
import dev.flavius.botw.data.network.places.response.ForwardGeocodingResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import javax.inject.Inject

class PlacesApi @Inject constructor(
    @PlacesApi private val httpClient: HttpClient
) {
    suspend fun get(
        placesQuery: PlacesQuery
    ): Result<List<Feature>> =
        runCatching {
            httpClient
                .get(placesQuery)
                .body<ForwardGeocodingResponse>()
                .features
        }

    object Endpoints {
        const val API_HOST = "api.mapbox.com"

        object Geocode {
            const val FORWARD = "/search/geocode/v6/forward"
        }
    }
}
