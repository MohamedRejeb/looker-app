package com.strawhat.looker.search.di

import com.strawhat.looker.search.data.remote.SearchApi
import com.strawhat.looker.search.data.repository.SearchRepositoryImpl
import com.strawhat.looker.search.domain.repository.SearchRepository
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
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchApi(
        @Named("AuthorizationRetrofit") retrofit: Retrofit
    ): SearchApi = retrofit.create(SearchApi::class.java)

    @Provides
    @Singleton
    fun provideSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository = searchRepositoryImpl

}