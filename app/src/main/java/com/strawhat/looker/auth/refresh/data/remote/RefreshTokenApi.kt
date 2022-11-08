package com.strawhat.looker.auth.refresh.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RefreshTokenApi {

    @POST("auth/refresh")
    suspend fun refresh(
        @Body refreshRequest: RefreshRequest
    ): Response<RefreshResponse>

}