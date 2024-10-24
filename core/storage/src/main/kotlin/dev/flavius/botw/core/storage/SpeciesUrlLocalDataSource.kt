package dev.flavius.botw.core.storage

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeciesUrlLocalDataSource @Inject constructor(
    private val speciesDatabase: SpeciesDatabase,
) {
}
