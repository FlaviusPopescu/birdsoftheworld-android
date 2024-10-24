package dev.flavius.botw.core.storage

import androidx.room.Dao
import androidx.room.Query
import dev.flavius.botw.core.storage.entities.SpeciesObservation

@Dao
interface SpeciesObservationsDao {
    @Query("SELECT * FROM speciesobservation")
    fun getObservations(): List<SpeciesObservation>
}
