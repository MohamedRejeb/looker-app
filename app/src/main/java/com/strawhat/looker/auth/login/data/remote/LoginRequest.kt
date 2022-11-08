package com.strawhat.looker.auth.login.data.remote

data class LoginRequest(
    val email: String,
    val password: String,
)
