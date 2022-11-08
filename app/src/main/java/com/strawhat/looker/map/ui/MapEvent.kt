package com.strawhat.looker.map.ui

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

sealed class MapEvent {
//    object ToggleFalloutMap: MapEvent()
    data class OnSpotClick(val scooterId: Int): MapEvent()
    data class OnScooterItemClick(val scooterId: Int): MapEvent()
//    data class OnMapLongClick(val latLng: LatLng): MapEvent()
//    data class OnInfoWindowLongClick(val spot: ParkingSpot): MapEvent()
//    data class OnInfoWindowLongClickNew(val marker: Marker): MapEvent()
//    data class GetDirections(val origin: LatLng, val dest: LatLng): MapEvent()
}
