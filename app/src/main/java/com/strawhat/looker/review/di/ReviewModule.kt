package com.strawhat.looker.review.di

import com.strawhat.looker.review.data.remote.ReviewApi
import com.strawhat.looker.review.data.repostiory.ReviewRepositoryImpl
import com.strawhat.looker.review.domain.repostiory.ReviewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReviewModule {

    @Provides
    @Singleton
    fun provideReviewApi(
        @Named("AuthorizationRetrofit") retrofit: Retrofit,
    ): ReviewApi = retrofit.create(ReviewApi::class.java)

    @Provides
    @Singleton
    fun provideReviewRepository(
        reviewRepositoryImpl: ReviewRepositoryImpl
    ): ReviewRepository = reviewRepositoryImpl

}