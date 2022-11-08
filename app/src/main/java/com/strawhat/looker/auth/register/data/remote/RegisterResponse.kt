package com.strawhat.looker.auth.register.data.remote

data class RegisterResponse(
    val accessToken: String,
    val refreshToken: String,
)
