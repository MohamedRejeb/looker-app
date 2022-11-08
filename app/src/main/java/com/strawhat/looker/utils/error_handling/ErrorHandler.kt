package com.strawhat.looker.utils.error_handling

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import timber.log.Timber
import kotlin.jvm.Throws

@Throws(
    InvalidDataException::class,
    UnauthorizedException::class,
    UnknownErrorException::class
)
inline fun <reified T, V> Response<T>.getResult(crossinline changer: (T) -> V): V {
    if (isSuccessful) {
        body()?.let { body ->
            return changer(body)
        }

        if (body() is T) {
            return changer(body() as T)
        }

        throw InvalidDataException(message())
    }

    val errorMessage = errorBody()?.string()

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()
    val adapter: JsonAdapter<ErrorResponse> = moshi.adapter(ErrorResponse::class.java)
    val errorResponse = try {
        errorMessage?.let { adapter.fromJson(it) }
    } catch(e: Exception) {
        e.printStackTrace()
        null
    }

    if (code() == 403)
        throw BannedException(errorResponse?.message)

    if (code() == 401)
        throw UnauthorizedException(errorResponse?.message)

    throw UnknownErrorException(code(), errorResponse?.message)
}