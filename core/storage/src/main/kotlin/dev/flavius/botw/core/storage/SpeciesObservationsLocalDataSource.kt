package dev.flavius.botw.core.storage

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeciesObservationsLocalDataSource @Inject constructor(
    private val speciesDatabase: SpeciesDatabase,
//    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun totalSpecies() = withContext(Dispatchers.IO) {
        speciesDatabase.speciesUrlDao().getObservations().size
    }
}
