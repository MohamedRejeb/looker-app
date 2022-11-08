package com.strawhat.looker.map.data.remote

import javax.inject.Inject

class MapRemoteDataSource @Inject constructor(
    private val mapApi: MapApi
){

    suspend fun getPlaces() = mapApi.getPlaces()

    suspend fun updateProductStatus(updateProductStatusRequest: UpdateProductStatusRequest) =
        mapApi.updateProductStatus(updateProductStatusRequest)

}