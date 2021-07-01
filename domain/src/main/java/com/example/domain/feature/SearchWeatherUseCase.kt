package com.example.domain.feature

import com.example.domain.base.DispatcherProvider
import com.example.domain.base.UseCase
import kotlinx.coroutines.flow.Flow

class SearchWeatherUseCase(
    override val dispatcherProvider: DispatcherProvider,
    private val weatherRepository: WeatherRepository
) : UseCase<CityWeather, String> {
    override fun run(params: String): Flow<Result<CityWeather>> {
        return weatherRepository.searchWeather(query = params)
    }
}