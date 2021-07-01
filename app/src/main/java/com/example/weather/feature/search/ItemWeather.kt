package com.example.weather.feature.search

import com.example.weather.base.adapter.ItemDifferent

data class ItemWeather(
    val date: String,
    val aveTemp: String,
    val pressure: String,
    val humidity: String,
    val desc: String
) : ItemDifferent {
    override val type: Int
        get() = 0
}