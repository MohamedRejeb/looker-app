package com.strawhat.looker.map.domain.repository

import com.strawhat.looker.map.domain.model.Place

interface MapRepository {

    suspend fun getPlaces(): List<Place>

    suspend fun updateProductStatus(
        productId: String,
        placeId: String,
        isAvailable: Boolean,
    )

}