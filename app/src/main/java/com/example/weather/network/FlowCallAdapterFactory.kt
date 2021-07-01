package com.example.weather.network

import com.example.domain.failure.ApiErrorResponse
import com.example.domain.failure.Failure
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FlowCallAdapterFactory(private val networkConnection: NetworkConnection) :
    CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Flow::class.java != getRawType(returnType)) {
            return null
        }
        check(returnType is ParameterizedType) {
            "Flow return type must be parameterized as Flow<ApiResponse<Foo>>"
        }
        val responseType = getParameterUpperBound(0, returnType)

        return BodyCallAdapter<Any>(
            responseType = responseType,
            networkConnection = networkConnection,
        )
    }

    private class BodyCallAdapter<T : Any>(
        private val responseType: Type,
        private val networkConnection: NetworkConnection,
    ) : CallAdapter<T, Flow<T>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Flow<T> = flow {
            if (!networkConnection.isConnected()) throw Failure.NetworkError
            emit(call.awaitBody())
        }
    }
}

suspend fun <T> Call<T>.awaitBody(): T {
    return suspendCancellableCoroutine { continuation ->
        continuation.invokeOnCancellation {
            cancel()
        }

        val response = try {
            execute()
        } catch (t: Throwable) {
            continuation.resumeWithException(Failure.UnknownError(t))
            return@suspendCancellableCoroutine
        }
        if (!response.isSuccessful) {
            continuation.resumeWithException(parseApiError(response))
            return@suspendCancellableCoroutine
        }

        when (val body = response.body()) {
            null -> {
                val invocation = request().tag(Invocation::class.java)!!
                val method = invocation.method()
                val e = Failure.BodyEmpty(
                    "Response from " +
                            method.declaringClass.name +
                            '.' +
                            method.name +
                            " was null but response body type was declared as non-null"
                )
                continuation.resumeWithException(e)
            }
            else -> {
                continuation.resume(body)
            }
        }
    }
}

fun <T> parseApiError(response: Response<T>): Throwable {
    val errorBody = response.errorBody()
    return if (errorBody != null) {
        try {
            val apiErrorResponse: ApiErrorResponse = Gson().fromJson(
                errorBody.charStream(),
                ApiErrorResponse::class.java
            )
            Failure.ApiError(
                responseCode = response.code(),
                code = apiErrorResponse.cod,
                msg = apiErrorResponse.message
            )
        } catch (e: Exception) {
            Failure.ApiError(
                responseCode = response.code(),
                msg = response.message()
            )
        }
    } else
        Failure.ApiError(
            responseCode = response.code(),
            msg = response.message()
        )
}
