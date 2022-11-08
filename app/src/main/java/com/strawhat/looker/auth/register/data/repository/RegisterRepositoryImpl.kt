package com.strawhat.looker.auth.register.data.repository

import com.strawhat.looker.auth.register.data.remote.RegisterRemoteDataSource
import com.strawhat.looker.auth.login.data.remote.LoginRequest
import com.strawhat.looker.auth.model.Token
import com.strawhat.looker.auth.register.domain.repository.RegisterRepository
import com.strawhat.looker.utils.error_handling.getResult
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val remote: RegisterRemoteDataSource,
): RegisterRepository {

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Token {
        val firstNameResponseBody = firstName.toRequestBody("text/plain".toMediaTypeOrNull())
        val lastNameResponseBody = lastName.toRequestBody("text/plain".toMediaTypeOrNull())
        val emailResponseBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val passwordResponseBody = password.toRequestBody("text/plain".toMediaTypeOrNull())
        val passwordConfirmResponseBody = passwordConfirm.toRequestBody("text/plain".toMediaTypeOrNull())

        return remote.register(
            params = hashMapOf(
                "firstName" to firstNameResponseBody,
                "lastName" to lastNameResponseBody,
                "email" to emailResponseBody,
                "password" to passwordResponseBody,
                "passwordConfirm" to passwordConfirmResponseBody,
            )
        ).getResult { Token(
            accessToken = it.accessToken,
            refreshToken = it.refreshToken,
        ) }
    }
}