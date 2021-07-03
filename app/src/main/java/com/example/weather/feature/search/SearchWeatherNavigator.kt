package com.example.weather.feature.search

import com.example.weather.R
import com.example.weather.feature.detail.DetailWeatherFragment
import com.example.weather.navigator.BaseNavigator
import com.example.weather.navigator.BaseNavigatorImpl
import com.example.weather.util.navOptions
import com.example.weather.util.safeNavigate

interface SearchWeatherNavigator : BaseNavigator {
    fun openDetail(itemWeather: ItemWeather)
}

class SearchWeatherNavigatorImpl : BaseNavigatorImpl(), SearchWeatherNavigator {
    override fun openDetail(itemWeather: ItemWeather) {
        requireNavigator().safeNavigate(
            destinationId = R.id.action_open_DetailWeatherFragment,
            bundle = {
                DetailWeatherFragment.toBundle(itemWeather)
            },
            navOptions = { navOptions(withAnim = true) }
        )
    }

}