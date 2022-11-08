package com.strawhat.looker.user_prefs.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.strawhat.looker.UserPreferences
import com.strawhat.looker.user_prefs.data.UserPreferencesRepositoryImpl
import com.strawhat.looker.user_prefs.data.UserPreferencesSerializer
import com.strawhat.looker.user_prefs.domain.repository.UserPreferencesRepository
import com.strawhat.looker.user_prefs.utils.USER_PREFS_DATA_STORE_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserPreferencesModule {

    @Singleton
    @Provides
    fun provideUserPreferencesProtoDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            produceFile = { appContext.dataStoreFile(USER_PREFS_DATA_STORE_FILE_NAME) },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(
        userPreferencesRepositoryImpl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository = userPreferencesRepositoryImpl

}