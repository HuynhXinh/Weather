package com.example.weather.di

import com.example.weather.feature.search.SearchForeCaseNavigator
import com.example.weather.feature.search.SearchForeCaseNavigatorImpl
import org.koin.dsl.module

val navigatorModule = module {
    single<SearchForeCaseNavigator> { SearchForeCaseNavigatorImpl() }
}