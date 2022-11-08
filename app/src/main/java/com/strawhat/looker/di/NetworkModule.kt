package com.strawhat.looker.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.strawhat.looker.BuildConfig
import com.strawhat.looker.auth.refresh.domain.TokenAuthenticator
import com.strawhat.looker.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("NoAuthorizationHttpClient")
    fun provideNoAuthHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Accept", "application/json")
                }.build())
            }.also { client ->
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    try {
                        client.addInterceptor(logging)
                    } catch (e: SocketTimeoutException) {
                        e.printStackTrace()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }.build()
    }

    @Singleton
    @Provides
    @Named("AuthorizationHttpClient")
    fun provideAuthHttpClient(
        @Named("NoAuthorizationHttpClient") okHttpClient: OkHttpClient,
        authenticator: TokenAuthenticator
    ): OkHttpClient {
        return okHttpClient.newBuilder()
            .also { client ->
                client.authenticator(authenticator)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): MoshiConverterFactory {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
        return MoshiConverterFactory.create(moshi)
    }

    @Singleton
    @Provides
    @Named("NoAuthorizationRetrofit")
    fun provideNoAuthRetrofitInstance(
        @Named("NoAuthorizationHttpClient")
        okHttpClient: OkHttpClient,
        converterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    @Named("AuthorizationRetrofit")
    fun provideAuthRetrofitInstance(
        @Named("AuthorizationHttpClient")
        okHttpClient: OkHttpClient,
        converterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

}