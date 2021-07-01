package com.example.data.di

import com.example.data.feature.WeatherDtoMapper
import com.example.data.feature.WeatherDtoMapperImpl
import com.example.data.feature.WeatherRepositoryImpl
import com.example.domain.feature.WeatherRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<WeatherDtoMapper> { WeatherDtoMapperImpl() }
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            weatherService = get(),
            weatherDtoMapper = get()
        )
    }
}