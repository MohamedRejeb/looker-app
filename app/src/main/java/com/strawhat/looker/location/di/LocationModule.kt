package com.strawhat.looker.location.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.strawhat.looker.Location
import com.strawhat.looker.location.data.LocationRepositoryImpl
import com.strawhat.looker.location.data.LocationSerializer
import com.strawhat.looker.location.domain.repository.LocationRepository
import com.strawhat.looker.location.utils.LOCATION_DATA_STORE_FILE_NAME
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
object LocationModule {

    @Singleton
    @Provides
    fun provideLocationProtoDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<Location> {
        return DataStoreFactory.create(
            serializer = LocationSerializer,
            produceFile = { appContext.dataStoreFile(LOCATION_DATA_STORE_FILE_NAME) },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository = locationRepositoryImpl

}