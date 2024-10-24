package dev.flavius.botw.core.data

import dev.flavius.botw.core.model.PlaceSuggestion
import dev.flavius.botw.data.network.places.PlacesApi
import dev.flavius.botw.data.network.places.request.PlacesQuery
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val placesApi: PlacesApi,
) {
    suspend fun getPlaces(query: String) =
        when {
            query.isEmpty() -> emptyList()
            else -> {
                placesApi
                    .get(PlacesQuery(query))
                    .getOrNull()
                    ?.map {
                        PlaceSuggestion(
                            name = it.properties.displayName,
                            address = it.properties.fullAddress,
                            latitude = it.properties.coordinates.latitude,
                            longitude = it.properties.coordinates.longitude,
                        )
                    }
                    ?: emptyList()
            }
        }
}
