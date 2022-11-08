package com.strawhat.looker.auth.login.data.repository

import com.strawhat.looker.auth.login.data.remote.LoginRemoteDataSource
import com.strawhat.looker.auth.register.data.remote.RegisterRemoteDataSource
import com.strawhat.looker.auth.login.data.remote.LoginRequest
import com.strawhat.looker.auth.login.domain.repository.LoginRepository
import com.strawhat.looker.auth.model.Token
import com.strawhat.looker.auth.register.domain.repository.RegisterRepository
import com.strawhat.looker.utils.error_handling.getResult
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val remote: LoginRemoteDataSource,
): LoginRepository {

    override suspend fun login(email: String, password: String): Token {
        return remote.login(
            LoginRequest(
                email = email,
                password = password
            )
        ).getResult { Token(
            accessToken = it.accessToken,
            refreshToken = it.refreshToken,
        ) }
    }
}