package com.example.weather.feature.search

import androidx.lifecycle.viewModelScope
import com.example.domain.feature.SearchWeatherUseCase
import com.example.weather.base.BaseViewModel
import com.example.weather.util.FailureHandler
import com.example.weather.util.SingleLiveEvent

class WeatherViewModel(
    private val searchWeatherUseCase: SearchWeatherUseCase,
    private val weatherMapper: WeatherMapper,

    failureHandler: FailureHandler
) : BaseViewModel(failureHandler) {
    val onSearchSuccess = SingleLiveEvent<List<ItemWeather>>()

    fun search(query: String) {
        loading.value = true

        searchWeatherUseCase(scope = viewModelScope, query) { result ->
            loading.value = false

            result.fold(
                onSuccess = {
                    onSearchSuccess.value = weatherMapper.toItemWeathers(it)
                },
                onFailure = {
                    handleFailure(it)
                }
            )
        }
    }

}