package com.strawhat.looker.map.data.remote

import com.squareup.moshi.Json

data class PlacesResponse(
    val payload: PlacesPayload
)

data class PlacesPayload(
    val places: List<PlaceResponse>
)

data class PlaceResponse(
    @Json(name = "_id")
    val id: String,
    val name: String,
    val slides: List<SlideResponse>,
    val lat: Double,
    val lan: Double,
    val description: String? = null,
    val category: CategoryResponse,
)

data class SlideResponse(
    @Json(name = "_id")
    val id: String,
    val url: String,
)

data class CategoryResponse(
    @Json(name = "_id")
    val id: String,
    val name: String,
    val products: List<ProductResponse>,
    val img: String,
)

data class ProductResponse(
    @Json(name = "_id")
    val id: String,
    val name: String,
    val img: String,
    val isAvailable: Boolean?,
)