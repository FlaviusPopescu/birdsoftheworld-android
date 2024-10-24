package dev.flavius.botw.feature.nearby

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.flavius.botw.data.SpeciesObservationsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearbyViewModel @Inject constructor(
    private val speciesObservationsRepository: SpeciesObservationsRepository
) : ViewModel() {
    val message = "Android (API ${Build.VERSION.SDK_INT})"

    var hasLocationPermission = false

    init {
        viewModelScope.launch {
            speciesObservationsRepository.getNearbyObservations(0f, 0f)
        }
    }
}
