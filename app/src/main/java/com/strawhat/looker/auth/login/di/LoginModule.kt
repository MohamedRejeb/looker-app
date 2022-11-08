package com.strawhat.looker.auth.login.di

import com.strawhat.looker.auth.login.data.remote.LoginApi
import com.strawhat.looker.auth.login.data.repository.LoginRepositoryImpl
import com.strawhat.looker.auth.login.domain.repository.LoginRepository
import com.strawhat.looker.auth.register.data.repository.RegisterRepositoryImpl
import com.strawhat.looker.auth.register.domain.repository.RegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideLoginApi(
        @Named("NoAuthorizationRetrofit") retrofit: Retrofit
    ): LoginApi = retrofit.create(LoginApi::class.java)

    @Provides
    @Singleton
    fun provideLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository = loginRepositoryImpl

}