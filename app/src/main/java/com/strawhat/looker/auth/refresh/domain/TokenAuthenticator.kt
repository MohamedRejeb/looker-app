package com.strawhat.looker.auth.refresh.domain

import com.auth0.android.jwt.JWT
import com.strawhat.looker.auth.model.Token
import com.strawhat.looker.user_prefs.data.UserPreferencesRepositoryImpl
import com.strawhat.looker.auth.refresh.data.remote.RefreshResponse
import com.strawhat.looker.auth.refresh.domain.repository.RefreshTokenRepository
import com.strawhat.looker.utils.error_handling.getResult
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.util.Date
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val userPreferences: UserPreferencesRepositoryImpl,
    private val refreshTokenRepository: RefreshTokenRepository,
): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val tokens = getUpdatedToken() ?: return@runBlocking null

            userPreferences.updateTokens(
                tokens.accessToken,
                tokens.refreshToken
            )
            response.request.newBuilder()
                .header("Authorization", "Bearer ${tokens.accessToken}")
                .build()
        }
    }

    private suspend fun getUpdatedToken(): Token? {
        val accessToken = userPreferences.userPreferencesFlow.first().accessToken
        val refreshToken = userPreferences.userPreferencesFlow.first().refreshToken

        if (isTokenValid(accessToken)) {
            return Token(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        }

        return try {
            val token = refreshTokenRepository.refresh(refreshToken)
            token
        } catch(e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        fun isTokenValid(token: String): Boolean {
            val jwt = try {
                JWT(token)
            } catch(e: Exception) {
                return false
            }

            val iat = jwt.getClaim("iat").asLong() ?: return false
            val exp = jwt.getClaim("exp").asLong() ?: return false
            val now = Date().time / 1000

            if (now < iat) return false
            if (now + 10 > exp) return false
            return true
        }
    }

}