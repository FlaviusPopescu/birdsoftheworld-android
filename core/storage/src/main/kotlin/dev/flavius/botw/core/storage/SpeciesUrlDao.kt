package dev.flavius.botw.core.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.flavius.botw.core.storage.entities.SpeciesUrl

@Dao
interface SpeciesUrlDao {
    @Query("SELECT * FROM speciesurl WHERE species_code = :speciesCode LIMIT 1")
    fun findByCode(speciesCode: String): SpeciesUrl

    @Query("SELECT * FROM speciesurl")
    fun totalSpecies(): List<SpeciesUrl>
}
