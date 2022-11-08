package com.strawhat.looker.location.domain.use_case

import com.google.android.gms.maps.model.LatLng
import com.strawhat.looker.location.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationFlowUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    operator fun invoke(): Flow<LatLng> {
        return locationRepository.locationFlow()
    }

}