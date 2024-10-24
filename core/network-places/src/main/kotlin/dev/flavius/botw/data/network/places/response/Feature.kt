package dev.flavius.botw.data.network.places.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Feature(
    @SerialName("type") val type: String,
    @SerialName("geometry") val geometry: Geometry,
    @SerialName("properties") val properties: Properties,
) {
    @Serializable
    data class Geometry(
        @SerialName("type") val type: String,
        @SerialName("coordinates") val latLong: List<Float>,
    )

    @Serializable
    data class Properties(
        @SerialName("full_address") val fullAddress: String,
        @SerialName("name_preferred") val displayName: String,
        @SerialName("coordinates") val coordinates: Coordinates,
    ) {
        @Serializable
        data class Coordinates(
            @SerialName("latitude") val latitude: Float,
            @SerialName("longitude") val longitude: Float,
        )
    }
}
