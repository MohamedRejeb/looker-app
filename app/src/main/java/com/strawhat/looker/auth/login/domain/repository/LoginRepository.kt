package com.strawhat.looker.auth.login.domain.repository

import com.strawhat.looker.auth.model.Token

interface LoginRepository {

    suspend fun login(
        email: String,
        password: String,
    ): Token

}