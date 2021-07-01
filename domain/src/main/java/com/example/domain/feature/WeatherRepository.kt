package com.example.domain.feature

import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun searchWeather(query: String): Flow<Result<CityWeather>>
}