package com.strawhat.looker.location.data

import androidx.datastore.core.DataStore
import com.google.android.gms.maps.model.LatLng
import com.strawhat.looker.Location
import com.strawhat.looker.location.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationStore: DataStore<Location>
): LocationRepository {

    override fun locationFlow(): Flow<LatLng> {
        return locationStore.data
            .map {
                LatLng(it.lat, it.long)
            }
            .catch { exception ->
                if (exception is IOException) {
                    Timber.e(exception, "Error reading sort order preferences.")
                    emit(LatLng(0.0, 0.0))
                } else {
                    Timber.e(exception, "Error reading sort order preferences.")
                    emit(LatLng(0.0, 0.0))
                }
            }
    }

    override suspend fun updateLocation(latLng: LatLng) {
        locationStore.updateData { preferences ->
            preferences.toBuilder()
                .setLat(latLng.latitude)
                .setLong(latLng.longitude)
                .build()
        }
    }

    override fun requestLocationFlow(): Flow<Boolean> {
        return locationStore.data
            .map { it.requestLocation }
            .catch { exception ->
                if (exception is IOException) {
                    Timber.e(exception, "Error reading sort order preferences.")
                    emit(false)
                } else {
                    Timber.e(exception, "Error reading sort order preferences.")
                    emit(false)
                }
            }
    }

    override suspend fun requestLocation() {
        locationStore.updateData { preferences ->
            preferences.toBuilder()
                .setRequestLocation(true)
                .build()
        }
    }

    override suspend fun clearRequestLocation() {
        locationStore.updateData { preferences ->
            preferences.toBuilder()
                .setRequestLocation(false)
                .build()
        }
    }

}