package com.strawhat.looker.user_prefs.data

import androidx.datastore.core.DataStore
import com.auth0.android.jwt.JWT
import com.strawhat.looker.UserPreferences
import com.strawhat.looker.user_prefs.domain.repository.UserPreferencesRepository
import com.strawhat.looker.utils.error_handling.UnauthorizedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okio.IOException
import timber.log.Timber
import javax.inject.Inject
import kotlin.jvm.Throws

class UserPreferencesRepositoryImpl @Inject constructor(
    private val userPreferencesStore: DataStore<UserPreferences>
): UserPreferencesRepository {

    override val userPreferencesFlow: Flow<UserPreferences> = userPreferencesStore.data
        .catch { exception ->
            if (exception is IOException) {
                Timber.e(exception, "Error reading sort order preferences.")
                emit(UserPreferences.getDefaultInstance())
            } else {
                Timber.e(exception, "Error reading sort order preferences.")
                emit(UserPreferences.getDefaultInstance())
            }
        }

    override suspend fun updateAccessToken(accessToken: String) {
        withContext(Dispatchers.IO) {
            userPreferencesStore.updateData { preferences ->
                preferences.toBuilder()
                    .setAccessToken(accessToken)
                    .build()
            }
        }
    }

    override suspend fun updateTokens(accessToken: String, refreshToken: String) {
        withContext(Dispatchers.IO) {
            userPreferencesStore.updateData { preferences ->
                preferences.toBuilder()
                    .setAccessToken(accessToken)
                    .setRefreshToken(refreshToken)
                    .build()
            }
        }
    }

    override suspend fun clearTokens() {
        withContext(Dispatchers.IO) {
            userPreferencesStore.updateData { preferences ->
                preferences.toBuilder()
                    .clearAccessToken() 
                    .clearRefreshToken()
                    .build()
            }
        }
    }

    @Throws(UnauthorizedException::class)
    override suspend fun getUserId(): String {
        val token = userPreferencesFlow.first().refreshToken
        if (token.isNullOrEmpty()) throw UnauthorizedException("")

        return JWT(token).getClaim("id").asString() ?: throw UnauthorizedException("")
    }

}