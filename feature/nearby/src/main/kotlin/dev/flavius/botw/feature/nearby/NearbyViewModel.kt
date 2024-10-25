package dev.flavius.botw.feature.nearby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.flavius.botw.core.data.PlacesRepository
import dev.flavius.botw.core.data.SpeciesObservationsRepository
import dev.flavius.botw.core.model.PlaceSuggestion
import dev.flavius.botw.core.model.SpeciesObservation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearbyViewModel @Inject constructor(
    private val speciesObservationsRepository: SpeciesObservationsRepository,
    private val placesRepository: PlacesRepository,
) : ViewModel() {

    fun searchPlacesByQuery(query: String) {
        _placeSuggestions.value = emptyList()
        viewModelScope.launch {
            _placeSuggestions.value = placesRepository.getPlaces(query)
        }
    }

    fun searchObservationsByPlace(placeSuggestion: PlaceSuggestion) {
        viewModelScope.launch {
            _nearbyObservations.value = speciesObservationsRepository.getNearbyObservations(
                latitude = placeSuggestion.latitude,
                longitude = placeSuggestion.longitude,
            )
        }
    }

    var hasLocationPermission = false

    private val _nearbyObservations = MutableStateFlow<List<SpeciesObservation>>(emptyList())
    val nearbyObservations = _nearbyObservations.asStateFlow()

    private val _placeSuggestions = MutableStateFlow<List<PlaceSuggestion>>(emptyList())
    val placeSuggestions = _placeSuggestions.asStateFlow()

    init {
        viewModelScope.launch {
            _nearbyObservations.value = speciesObservationsRepository.getSampleObservations()
        }
    }
}
