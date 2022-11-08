package com.strawhat.looker.location.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.strawhat.looker.Location
import java.io.InputStream
import java.io.OutputStream

object LocationSerializer: Serializer<Location> {

    override val defaultValue: Location
        get() = Location.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Location {
        try {
            return Location.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Location, output: OutputStream) = t.writeTo(output)

}