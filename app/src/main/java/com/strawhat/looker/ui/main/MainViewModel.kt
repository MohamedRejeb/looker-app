package com.strawhat.looker.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.strawhat.looker.location.domain.use_case.ClearRequestLocationUseCase
import com.strawhat.looker.location.domain.use_case.RequestLocationFlowUseCase
import com.strawhat.looker.location.domain.use_case.UpdateLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    requestLocationFlowUseCase: RequestLocationFlowUseCase,
    private val updateLocationUseCase: UpdateLocationUseCase,
    private val clearRequestLocationUseCase: ClearRequestLocationUseCase,
): ViewModel() {

    val requestLocation = requestLocationFlowUseCase().stateIn(
        scope = viewModelScope + Dispatchers.IO,
        started = SharingStarted.Lazily,
        initialValue = false,
    )

    fun updateLocation(latLng: LatLng) {
        Timber.d("update location $latLng")
        viewModelScope.launch {
            updateLocationUseCase(latLng = latLng)
        }
    }

    fun clearRequestLocation() {
        viewModelScope.launch {
            clearRequestLocationUseCase()
        }
    }

}