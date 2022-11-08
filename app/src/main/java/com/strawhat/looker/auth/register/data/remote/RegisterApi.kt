package com.strawhat.looker.auth.register.data.remote

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface RegisterApi {

    @Multipart
    @POST("auth/signup")
    suspend fun register(
        @PartMap params: HashMap<String, RequestBody>,
    ): Response<RegisterResponse>

}