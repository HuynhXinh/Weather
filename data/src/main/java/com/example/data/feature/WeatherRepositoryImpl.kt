package com.example.data.feature

import com.example.domain.feature.CityWeather
import com.example.domain.feature.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeatherRepositoryImpl(
    private val weatherService: WeatherService,
    private val weatherDtoMapper: WeatherDtoMapper
) : WeatherRepository {

    //https://api.openweathermap.org/data/
    // 2.5/forecast/daily?
    // q=saigon
    // &cnt=7
    // &appid=60c6fbeb4b93ac653c492ba806fc346d

    override fun searchWeather(query: String): Flow<Result<CityWeather>> {
        return weatherService.searchWeather(
            query = query,
            cnt = "7",
            appid = "60c6fbeb4b93ac653c492ba806fc346d",
        ).map {
            Result.success(weatherDtoMapper.toWeather(it))
        }
    }
}