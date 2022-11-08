package com.strawhat.looker.auth.register.data.remote

import okhttp3.RequestBody
import javax.inject.Inject

class RegisterRemoteDataSource @Inject constructor(
    private val registerApi: RegisterApi,
) {

    suspend fun register(params: HashMap<String, RequestBody>) =
        registerApi.register(params = params)

}