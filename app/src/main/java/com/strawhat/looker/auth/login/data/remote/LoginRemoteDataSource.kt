package com.strawhat.looker.auth.login.data.remote

import javax.inject.Inject

class LoginRemoteDataSource @Inject constructor(
    private val loginApi: LoginApi,
) {

    suspend fun login(loginRequest: LoginRequest) =
        loginApi.register(loginRequest)

}