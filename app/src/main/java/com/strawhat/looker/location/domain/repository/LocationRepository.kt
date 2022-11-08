package com.strawhat.looker.location.domain.repository

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun locationFlow(): Flow<LatLng>

    suspend fun updateLocation(latLng: LatLng)

    fun requestLocationFlow(): Flow<Boolean>

    suspend fun requestLocation()

    suspend fun clearRequestLocation()

}