package com.example.weather.feature.search

import com.example.domain.feature.Element
import com.example.domain.feature.ForeCast

interface ForeCastMapper {
    fun toItemForeCasts(foreCast: ForeCast): List<ItemWeather>
}

class ForeCastMapperImpl(private val timeFormatter: TimeFormatter) : ForeCastMapper {
    override fun toItemForeCasts(foreCast: ForeCast): List<ItemWeather> {
        return foreCast.elements.map { toItemForecast(it) }
    }

    private fun toItemForecast(element: Element): ItemWeather {
        return ItemWeather(
            date = timeFormatter.format(element.dt * 1000),
            aveTemp = formatTemp(element.temp.eve),
            pressure = "${element.pressure}",
            humidity = "${element.humidity}%",
            desc = element.weather.firstOrNull()?.description ?: "",
        )
    }

    // https://usma.org/metric-system-temperature#locale-notification
    private fun formatTemp(eve: Double): String {
        val degreesCelsius = eve - 273.15
        val round = String.format("%.2f", degreesCelsius)
        return "$round \u2103"
    }

}