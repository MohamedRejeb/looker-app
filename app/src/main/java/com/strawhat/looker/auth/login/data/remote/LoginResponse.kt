package com.strawhat.looker.auth.login.data.remote

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
)
