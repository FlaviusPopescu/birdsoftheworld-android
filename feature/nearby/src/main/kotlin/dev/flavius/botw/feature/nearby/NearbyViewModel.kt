package dev.flavius.botw.feature.nearby

import android.os.Build
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NearbyViewModel @Inject constructor() : ViewModel() {
    val message = "Android (API ${Build.VERSION.SDK_INT})"

    var hasLocationPermission = false
}
