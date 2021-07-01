package com.example.weather.network

import com.example.weather.BuildConfig

interface NetworkConfigs {
    object Keys {
        const val ACCEPT = "accept"
        const val ACCEPT_LANGUAGE = "accept-language"
        const val USER_AGENT = "User-Agent"
        const val CONTENT_TYPE = "content-type"
    }

    object Values {
        const val TIME_OUT = 60L
        const val ACCEPT_VALUE = "application/json"
    }

    val isLogging: Boolean get() = BuildConfig.LOGGING

    val baseUrl: String
    val acceptLanguage: String
    val userAgent: String
}

class NetworkConfigsImpl : NetworkConfigs {
    override val baseUrl: String get() = "https://api.openweathermap.org/data/"
    override val acceptLanguage: String get() = "en-US,en;q=0.9,vi;q=0.8"
    override val userAgent: String get() = "${BuildConfig.APP_NAME}/${BuildConfig.VERSION_NAME}"
}
