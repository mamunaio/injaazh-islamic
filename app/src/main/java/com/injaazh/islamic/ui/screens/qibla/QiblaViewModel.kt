package com.injaazh.islamic.ui.screens.qibla

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.Qibla
import com.injaazh.islamic.data.manager.LocationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QiblaViewModel @Inject constructor(
    private val locationHelper: LocationHelper
) : ViewModel() {

    private val _qiblaDirection = MutableStateFlow(0.0)
    val qiblaDirection: StateFlow<Double> = _qiblaDirection.asStateFlow()

    init {
        calculateQibla()
    }

    private fun calculateQibla() {
        viewModelScope.launch {
            val location = locationHelper.getCurrentLocation()
            val coordinates = if (location != null) {
                Coordinates(location.latitude, location.longitude)
            } else {
                Coordinates(23.8103, 90.4125) // Dhaka
            }
            _qiblaDirection.value = Qibla(coordinates).direction
        }
    }
}
