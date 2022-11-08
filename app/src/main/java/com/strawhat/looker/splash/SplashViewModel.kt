package com.strawhat.looker.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strawhat.looker.auth.refresh.domain.TokenAuthenticator.Companion.isTokenValid
import com.strawhat.looker.user_prefs.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
): ViewModel() {

    var navigateToLogin by mutableStateOf(false)
    var navigateToHome by mutableStateOf(false)

    fun onAppear() {
        viewModelScope.launch {
            delay(2000)
            val refreshToken = userPreferencesRepository.userPreferencesFlow.first().refreshToken ?: ""
            if (isTokenValid(refreshToken)) {
                navigateToHome = true
            } else {
                navigateToLogin = true
            }
        }
    }

}