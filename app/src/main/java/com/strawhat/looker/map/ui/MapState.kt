package com.strawhat.looker.map.ui

import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.compose.MapProperties

data class MapState(
    val properties: MapProperties = MapProperties(
        mapStyleOptions = MapStyleOptions(MapStyle.json),
        minZoomPreference = 8f
    ),
    val parkingSpots: List<Any> = emptyList(),
    val isFalloutMap: Boolean = true,
    val polylineOptions: PolylineOptions = PolylineOptions(),
    val scooterList: List<Any> = emptyList(),
    val selectedScooterId: Int = 1
)