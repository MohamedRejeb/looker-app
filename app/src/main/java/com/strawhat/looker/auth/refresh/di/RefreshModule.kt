package com.strawhat.looker.auth.refresh.di

import com.strawhat.looker.auth.refresh.data.remote.RefreshTokenApi
import com.strawhat.looker.auth.refresh.data.repository.RefreshTokenRepositoryImpl
import com.strawhat.looker.auth.refresh.domain.repository.RefreshTokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RefreshModule {

    @Provides
    @Singleton
    fun provideRefreshApi(
        @Named("NoAuthorizationRetrofit") retrofit: Retrofit
    ): RefreshTokenApi = retrofit.create(RefreshTokenApi::class.java)

    @Provides
    @Singleton
    fun provideRefreshTokenRepository(
        refreshTokenRepositoryImpl: RefreshTokenRepositoryImpl
    ): RefreshTokenRepository = refreshTokenRepositoryImpl

}