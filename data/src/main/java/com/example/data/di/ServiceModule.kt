package com.example.data.di

import com.example.data.feature.WeatherService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single { providedWeatherService(retrofit = get()) }
}

fun providedWeatherService(retrofit: Retrofit): WeatherService {
     return retrofit.create(WeatherService::class.java)
}
