package com.strawhat.looker.map.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface MapApi {

    @GET("user/places")
    suspend fun getPlaces(): Response<PlacesResponse>

    @PUT("user/places/update")
    suspend fun updateProductStatus(
        @Body updateProductStatusRequest: UpdateProductStatusRequest,
    ): Response<ResponseBody>

}