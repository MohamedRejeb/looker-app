package com.strawhat.looker.auth.refresh.data.repository

import com.strawhat.looker.auth.model.Token
import com.strawhat.looker.auth.refresh.data.remote.RefreshRequest
import com.strawhat.looker.auth.refresh.data.remote.RefreshTokenApi
import com.strawhat.looker.auth.refresh.domain.repository.RefreshTokenRepository
import com.strawhat.looker.utils.error_handling.getResult
import javax.inject.Inject

class RefreshTokenRepositoryImpl @Inject constructor(
    private val refreshTokenApi: RefreshTokenApi
): RefreshTokenRepository {

    override suspend fun refresh(refreshToken: String): Token {
        return refreshTokenApi.refresh(
            refreshRequest = RefreshRequest(refreshToken)
        ).getResult { Token(
            accessToken = it.accessToken,
            refreshToken = it.refreshToken,
        ) }
    }

}