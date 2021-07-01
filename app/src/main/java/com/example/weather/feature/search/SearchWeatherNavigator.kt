package com.example.weather.feature.search

import com.example.weather.R
import com.example.weather.navigator.BaseNavigator
import com.example.weather.navigator.BaseNavigatorImpl
import com.example.weather.util.navOptions
import com.example.weather.util.safeNavigate

interface SearchWeatherNavigator : BaseNavigator {
    fun openDetail()
}

class SearchWeatherNavigatorImpl : BaseNavigatorImpl(), SearchWeatherNavigator {
    override fun openDetail() {
        requireNavigator().safeNavigate(
            destinationId = R.id.action_open_DetailWeatherFragment,
            navOptions = { navOptions(withAnim = true) }
        )
    }

}