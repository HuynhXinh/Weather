package com.example.weather.feature.search

import androidx.lifecycle.viewModelScope
import com.example.domain.feature.SearchForeCastUseCase
import com.example.weather.base.BaseViewModel
import com.example.weather.util.SingleLiveEvent

class WeatherViewModel(
    private val searchForeCastUseCase: SearchForeCastUseCase,
    private val foreCastMapper: ForeCastMapper
) :
    BaseViewModel() {
    val onSearchSuccess = SingleLiveEvent<List<ItemWeather>>()

    fun search(query: String) {
        loading.value = true

        searchForeCastUseCase(scope = viewModelScope, query) { result ->
            loading.value = false

            result.fold(
                onSuccess = {
                    onSearchSuccess.value = foreCastMapper.toItemForeCasts(it)
                },
                onFailure = {
                    error.value = it
                }
            )
        }
    }

}