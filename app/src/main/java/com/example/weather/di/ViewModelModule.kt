package com.example.weather.di

import com.example.weather.feature.search.*
import org.koin.dsl.module

val viewModelModule = module {
    single { WeatherViewModel(searchForeCastUseCase = get(), foreCastMapper = get()) }

    factory<ForeCastMapper> { ForeCastMapperImpl(timeFormatter = get()) }

    factory<TimeFormatter> { TimeFormatterImpl() }
}