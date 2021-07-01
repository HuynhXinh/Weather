package com.example.data.feature

import com.example.domain.feature.ForeCast
import com.example.domain.feature.WeatherRepository
import com.github.ajalt.timberkt.Timber
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import retrofit2.http.Query

class WeatherRepositoryImpl(
    private val weatherService: WeatherService,
    private val weatherDtoMapper: WeatherDtoMapper
) : WeatherRepository {

    //https://api.openweathermap.org/data/
    // 2.5/forecast/daily?
    // q=saigon
    // &cnt=7
    // &appid=60c6fbeb4b93ac653c492ba806fc346d

    override fun searchForeCast(query: String): Flow<Result<ForeCast>> {
        return weatherService.searchForeCast(
            query = query,
            cnt = "7",
            appid = "60c6fbeb4b93ac653c492ba806fc346d",
        ).map {
            Result.success(weatherDtoMapper.toForeCast(it))
        }
    }
}