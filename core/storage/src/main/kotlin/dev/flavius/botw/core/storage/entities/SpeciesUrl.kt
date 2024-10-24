package dev.flavius.botw.core.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SpeciesUrl(
    @PrimaryKey @ColumnInfo("species_code") val speciesCode: String,
    @ColumnInfo("url") val url: String,
)