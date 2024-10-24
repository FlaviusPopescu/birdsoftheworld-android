package dev.flavius.botw.core.storage

import dev.flavius.botw.core.di.dispatchers.Io
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpeciesObservationsLocalDataSource @Inject constructor(
    private val speciesDatabase: SpeciesDatabase,
    @Io private val dispatcher: CoroutineDispatcher,
) {
    suspend fun getSampleObservations() = withContext(dispatcher) {
        speciesDatabase.speciesUrlDao().getObservations()
    }
}
