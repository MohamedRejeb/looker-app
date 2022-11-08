package com.strawhat.looker.auth.register.ui

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strawhat.looker.auth.register.domain.repository.RegisterRepository
import com.strawhat.looker.user_prefs.domain.use_case.UpdateTokensUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val updateTokensUseCase: UpdateTokensUseCase,
): ViewModel() {

    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordConfirm by mutableStateOf("")

    var isLoading by mutableStateOf(false)

    var isSuccess by mutableStateOf(false)

    var isError by mutableStateOf(false)
    var errorMessage by mutableStateOf("")

    private var registerJob: Job? = null
    fun registerEvent() {
        if (registerJob?.isActive == true) return
        registerJob = viewModelScope.launch {
            val firstName = this@RegisterViewModel.firstName.trim()
            val lastName = this@RegisterViewModel.lastName.trim()
            val email = this@RegisterViewModel.email.trim()
            val password = this@RegisterViewModel.password.trim()
            val passwordConfirm = this@RegisterViewModel.passwordConfirm.trim()

            if (firstName.isBlank()) {
                isError = true
                errorMessage = "Please enter your first name"
                return@launch
            }

            if (lastName.isBlank()) {
                isError = true
                errorMessage = "Please enter your last name"
                return@launch
            }

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

            if (passwordConfirm.isBlank()) {
                isError = true
                errorMessage = "Please enter password confirmation"
                return@launch
            }

            if (firstName.length < 3) {
                isError = true
                errorMessage = "First name is too short"
                return@launch
            }

            if (lastName.length < 3) {
                isError = true
                errorMessage = "Last name is too short"
                return@launch
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                isError = true
                errorMessage = "Please enter a valid email"
                return@launch
            }

            if (password.length < 8) {
                isError = true
                errorMessage = "Password must be at least 8 characters"
                return@launch
            }

            if (password != passwordConfirm) {
                isError = true
                errorMessage = "Please make sure your passwords match"
                return@launch
            }

            isLoading = true
            isError = false

            try {
                val tokens = registerRepository.register(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password,
                    passwordConfirm = passwordConfirm,
                )

                updateTokensUseCase(
                    accessToken = tokens.accessToken,
                    refreshToken = tokens.refreshToken,
                )

                isSuccess = true
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.d("error message: ${e.message}")

                isError = true
                errorMessage = e.message
                    ?.replaceFirstChar { it.uppercase() }
                    ?: "Something went wrong"
            } finally {
                isLoading = false
            }
        }
    }

}