package com.example.data.feature

import com.example.domain.feature.CityWeather
import com.example.domain.feature.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class WeatherRepositoryImpl(
    private val weatherService: WeatherService,
    private val weatherDtoMapper: WeatherDtoMapper,
    private val memCache: MemCache
) : WeatherRepository {

    //https://api.openweathermap.org/data/
    // 2.5/forecast/daily?
    // q=saigon
    // &cnt=7
    // &appid=60c6fbeb4b93ac653c492ba806fc346d

    override fun searchWeather(query: String): Flow<Result<CityWeather>> {
        val cache = memCache.getOrNull(query)
        if (cache != null) {
            return flowOf(Result.success(cache))
        }

        return weatherService.searchWeather(
            query = query,
            cnt = "7",
            appid = "60c6fbeb4b93ac653c492ba806fc346d",
        ).map {
            val cityWeather = weatherDtoMapper.toWeather(it)
            memCache.cache(query, cityWeather)

            Result.success(cityWeather)
        }
    }
}