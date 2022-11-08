package com.strawhat.looker.location.domain.use_case

import com.google.android.gms.maps.model.LatLng
import com.strawhat.looker.location.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke() {
        locationRepository.requestLocation()
    }

}