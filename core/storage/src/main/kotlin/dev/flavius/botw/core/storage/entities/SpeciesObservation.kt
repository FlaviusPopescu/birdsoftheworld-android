package dev.flavius.botw.core.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = [
        "species_code",
        "location_id",
        "observation_date"
    ]
)
data class SpeciesObservation(
    @ColumnInfo(name = "species_code") val speciesCode: String,
    @ColumnInfo(name = "common_name") val commonName: String,
    @ColumnInfo(name = "scientific_name") val scientificName: String,
    @ColumnInfo(name = "location_id") val locationId: String,
    @ColumnInfo(name = "location_name") val locationName: String,
    @ColumnInfo(name = "observation_date") val observationDate: String,
    @ColumnInfo(name = "observation_count") val observationCount: Int,
    @ColumnInfo(name = "latitude") val latitude: Float,
    @ColumnInfo(name = "longitude") val longitude: Float,
    @ColumnInfo(name = "is_valid") val isValid: Boolean,
    @ColumnInfo(name = "is_reviewed") val isReviewed: Boolean,
    @ColumnInfo(name = "is_location_private") val isLocationPrivate: Boolean,
    @ColumnInfo(name = "subregion_id") val subregionId: String,
)
