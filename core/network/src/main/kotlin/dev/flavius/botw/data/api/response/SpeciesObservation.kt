package dev.flavius.botw.data.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpeciesObservation(
    @SerialName("speciesCode") val speciesCode: String,
    @SerialName("comName") val commonName: String,
    @SerialName("sciName") val scientificName: String,
    @SerialName("locId") val locationId: String,
    @SerialName("locName") val locationName: String,
    @SerialName("subId") val subregionId: String,
    @SerialName("obsDt") val observationDate: String,
    @SerialName("howMany") val observationCount: Int,
    @SerialName("lat") val latitude: Float,
    @SerialName("lng") val longitude: Float,
    @SerialName("obsValid") val isValid: Boolean,
    @SerialName("obsReviewed") val isReviewed: Boolean,
    @SerialName("locationPrivate") val isLocationPrivate: Boolean,
)
