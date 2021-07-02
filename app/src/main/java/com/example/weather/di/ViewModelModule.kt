package com.example.weather.di

import com.example.weather.feature.search.*
import org.koin.dsl.module

val viewModelModule = module {
    single {
        WeatherViewModel(
            searchWeatherUseCase = get(),
            weatherMapper = get(),
            failureHandler = get()
        )
    }

    factory<WeatherMapper> { WeatherMapperImpl(timeFormatter = get()) }

    factory<TimeFormatter> { TimeFormatterImpl() }
}