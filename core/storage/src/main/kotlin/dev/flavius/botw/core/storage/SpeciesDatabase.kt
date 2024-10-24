package dev.flavius.botw.core.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.flavius.botw.core.storage.entities.SpeciesObservation

@Database(
    entities = [
        SpeciesObservation::class
    ],
    version = 1,
)
abstract class SpeciesDatabase: RoomDatabase() {
    abstract fun speciesUrlDao(): SpeciesObservationsDao
}
