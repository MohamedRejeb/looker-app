package com.strawhat.looker.user_prefs.domain.repository

import com.strawhat.looker.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    val userPreferencesFlow: Flow<UserPreferences>

    suspend fun updateAccessToken(accessToken: String)

    suspend fun updateTokens(accessToken: String, refreshToken: String)

    suspend fun clearTokens()

    suspend fun getUserId(): String

}