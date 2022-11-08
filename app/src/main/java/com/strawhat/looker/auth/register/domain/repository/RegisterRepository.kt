package com.strawhat.looker.auth.register.domain.repository

import com.strawhat.looker.auth.model.Token

interface RegisterRepository {

    suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        passwordConfirm: String,
    ): Token

}