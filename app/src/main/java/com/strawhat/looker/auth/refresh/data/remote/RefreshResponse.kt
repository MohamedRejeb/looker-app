package com.strawhat.looker.auth.refresh.data.remote

data class RefreshResponse (
    val accessToken: String,
    val refreshToken: String
)