package com.strawhat.looker.auth.login.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strawhat.looker.auth.login.domain.repository.LoginRepository
import com.strawhat.looker.auth.register.domain.repository.RegisterRepository
import com.strawhat.looker.user_prefs.domain.use_case.UpdateTokensUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val updateTokensUseCase: UpdateTokensUseCase,
): ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var isLoading by mutableStateOf(false)

    var isSuccess by mutableStateOf(false)

    var isError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    private var loginJob: Job? = null
    fun loginEvent() {
        if (loginJob?.isActive == true) return
        loginJob = viewModelScope.launch {
            val email = this@LoginViewModel.email.trim()
            val password = this@LoginViewModel.password.trim()

            if (email.isBlank()) {
                isError = true
                errorMessage = "Please enter your email"
                return@launch
            }

            if (password.isBlank()) {
                isError = true
                errorMessage = "Please enter your password"
                return@launch
            }

            isLoading = true
            isError = false

            try {
                val tokens = loginRepository.login(
                    email = email,
                    password = password,
                )

                updateTokensUseCase(
                    accessToken = tokens.accessToken,
                    refreshToken = tokens.refreshToken,
                )

                isSuccess = true
            } catch (e: Exception) {
                e.printStackTrace()

                isError = true
                errorMessage = "Email or password is incorrect"
            } finally {
                isLoading = false
            }
        }
    }

}