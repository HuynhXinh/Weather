package com.example.weather.feature.search

import android.os.Parcelable
import com.example.weather.base.adapter.ItemDifferent
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemWeather(
    val date: String,
    val aveTemp: String,
    val pressure: String,
    val humidity: String,
    val desc: String
) : ItemDifferent, Parcelable {
    override val type: Int
        get() = 0
}