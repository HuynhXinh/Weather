package com.example.domain.failure

sealed class Failure(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause) {

    data class BodyEmpty(val msg: String? = null) : Failure()

    data class ApiError(
        val responseCode: Int? = null,
        val code: String? = null,
        val msg: String? = null
    ) : Failure(message = msg)

    object NetworkError : Failure()

    data class UnknownError(val error: Throwable? = null) : Failure(cause = error)

    open class BusinessFailure(
        message: String? = null,
        cause: Throwable? = null
    ) : Failure(
        message = message,
        cause = cause
    )
}
