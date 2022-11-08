package com.strawhat.looker.auth.model

data class Token(
    val accessToken: String,
    val refreshToken: String,
)
