package com.example.weather.di

import com.example.weather.feature.search.SearchWeatherNavigator
import com.example.weather.feature.search.SearchWeatherNavigatorImpl
import org.koin.dsl.module

val navigatorModule = module {
    single<SearchWeatherNavigator> { SearchWeatherNavigatorImpl() }
}