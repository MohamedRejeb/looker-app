package com.strawhat.looker.auth.refresh.domain.repository

import com.strawhat.looker.auth.model.Token

interface RefreshTokenRepository {

    suspend fun refresh(refreshToken: String): Token

}