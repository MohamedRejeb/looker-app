package com.strawhat.looker.auth.login.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("auth/login")
    suspend fun register(
        @Body loginRequest: LoginRequest,
    ): Response<LoginResponse>

}