package com.strawhat.looker.location.domain.use_case

import com.google.android.gms.maps.model.LatLng
import com.strawhat.looker.location.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestLocationFlowUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    operator fun invoke(): Flow<Boolean> {
        return locationRepository.requestLocationFlow()
    }

}