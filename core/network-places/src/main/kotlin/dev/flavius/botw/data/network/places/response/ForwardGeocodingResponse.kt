package dev.flavius.botw.data.network.places.response

import kotlinx.serialization.Serializable

@Serializable
data class ForwardGeocodingResponse(
    val features: List<Feature>,
)
