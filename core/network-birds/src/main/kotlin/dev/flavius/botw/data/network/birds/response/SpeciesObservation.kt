package dev.flavius.botw.data.network.birds.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpeciesObservation(
    @SerialName("speciesCode") val speciesCode: String,
    @SerialName("comName") val commonName: String = "",
    @SerialName("sciName") val scientificName: String = "",
    @SerialName("locId") val locationId: String = "",
    @SerialName("locName") val locationName: String = "",
    @SerialName("subId") val subregionId: String = "",
    @SerialName("obsDt") val observationDate: String = "",
    @SerialName("howMany") val observationCount: Int = -1,
    @SerialName("lat") val latitude: Float,
    @SerialName("lng") val longitude: Float,
    @SerialName("obsValid") val isValid: Boolean = false,
    @SerialName("obsReviewed") val isReviewed: Boolean = false,
    @SerialName("locationPrivate") val isLocationPrivate: Boolean = false,
)
