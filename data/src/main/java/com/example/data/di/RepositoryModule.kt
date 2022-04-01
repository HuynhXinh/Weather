package com.example.data.di

import com.example.data.feature.*
import com.example.domain.feature.WeatherRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<WeatherDtoMapper> { WeatherDtoMapperImpl() }

    single<MemCache> { MemCacheImpl() }

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            weatherService = get(),
            weatherDtoMapper = get(),
            memCache = get()
        )
    }
}