package com.example.weather.util

import com.example.domain.failure.Failure
import com.example.weather.R
import java.net.HttpURLConnection

interface FailureHandler {
    fun getMsg(failure: Throwable): String
}

class FailureHandlerImpl(private val stringRes: StringRes) : FailureHandler {
    override fun getMsg(failure: Throwable): String {
        return when (failure) {
            is Failure.NetworkError -> stringRes.getString(R.string.msg_network_error)
            is Failure.ApiError -> getApiErrorMsg(failure)
            else -> stringRes.getString(R.string.msg_generic_error)
        }
    }

    private fun getApiErrorMsg(failure: Failure.ApiError): String {
        return if (failure.responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            stringRes.getString(R.string.msg_city_not_found)
        } else {
            stringRes.getString(R.string.msg_generic_error)
        }
    }

}
