package dev.flavius.botw.data.network.places.request

import dev.flavius.botw.data.network.places.PlacesApi
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName

@Resource(PlacesApi.Endpoints.Geocode.FORWARD)
class PlacesQuery(
    @SerialName("q") val query: String,
)
