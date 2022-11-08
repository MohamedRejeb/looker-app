package com.strawhat.looker.map.di

import com.strawhat.looker.map.data.remote.MapApi
import com.strawhat.looker.map.data.repository.MapRepositoryImpl
import com.strawhat.looker.map.domain.repository.MapRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapModule {

    @Provides
    @Singleton
    fun provideMapApi(
        @Named("AuthorizationRetrofit") retrofit: Retrofit,
    ): MapApi = retrofit.create(MapApi::class.java)

    @Provides
    @Singleton
    fun provideMapRepository(
        mapRepositoryImpl: MapRepositoryImpl
    ): MapRepository = mapRepositoryImpl

}