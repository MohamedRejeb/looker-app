package com.strawhat.looker.auth.register.di

import com.strawhat.looker.auth.register.data.repository.RegisterRepositoryImpl
import com.strawhat.looker.auth.register.domain.repository.RegisterRepository
import com.strawhat.looker.auth.register.data.remote.RegisterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterModule {

    @Provides
    @Singleton
    fun provideRegisterApi(
        @Named("NoAuthorizationRetrofit") retrofit: Retrofit
    ): RegisterApi = retrofit.create(RegisterApi::class.java)

    @Provides
    @Singleton
    fun provideRegisterRepository(
        registerRepositoryImpl: RegisterRepositoryImpl
    ): RegisterRepository = registerRepositoryImpl

}