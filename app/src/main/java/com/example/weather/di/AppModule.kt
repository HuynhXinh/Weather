package com.example.weather.di

import com.example.weather.util.FailureHandler
import com.example.weather.util.FailureHandlerImpl
import com.example.weather.util.StringRes
import com.example.weather.util.StringResImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<StringRes> { StringResImpl(androidContext()) }

    single<FailureHandler> { FailureHandlerImpl(stringRes = get()) }
}