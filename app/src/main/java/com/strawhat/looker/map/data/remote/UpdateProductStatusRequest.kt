package com.strawhat.looker.map.data.remote

data class UpdateProductStatusRequest(
    val product: String,
    val place: String,
    val isAvailable: Boolean,
)