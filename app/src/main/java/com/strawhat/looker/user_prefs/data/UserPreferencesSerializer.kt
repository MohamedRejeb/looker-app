package com.strawhat.looker.user_prefs.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.strawhat.looker.UserPreferences
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer: Serializer<UserPreferences> {

    override val defaultValue: UserPreferences
        get() = UserPreferences.getDefaultInstance()

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun readFrom(input: InputStream): UserPreferences {
        try {
            return UserPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: UserPreferences, output: OutputStream) = t.writeTo(output)

}