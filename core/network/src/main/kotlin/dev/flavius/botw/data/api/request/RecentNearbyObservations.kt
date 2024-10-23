package dev.flavius.botw.data.api.request

import dev.flavius.botw.data.api.BirdApi
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName

@Resource(BirdApi.Endpoints.Observations.RECENT_NEARBY) class RecentNearbyObservations(
    @SerialName("lat") val latitude: Float,
    @SerialName("lng") val longitude: Float,
    @SerialName("back") val daysBehind: Int = 14,
    @SerialName("dist") val radiusKm: Int = 25,
)
