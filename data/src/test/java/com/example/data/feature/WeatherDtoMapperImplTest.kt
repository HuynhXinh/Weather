package com.example.data.feature

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Test

/**
 * ~ This is just lazy testing :)))
 * ~ I want to show the approach testing
 */
class WeatherDtoMapperImplTest {
    private val weatherDtoMapper = WeatherDtoMapperImpl()

    @Test
    fun `verify convert CityWeatherDto to CityWeather`() {
        val cityWeather = weatherDtoMapper.toWeather(mockCityWeatherDto())

        MatcherAssert.assertThat(cityWeather.city.id, `is`(999))
    }

    private fun mockCityWeatherDto(): CityWeatherDto {
        return CityWeatherDto(
            city = CityDto(
                id = 999,
                name = "Sai Gon",
                coord = CoordDto(
                    lat = 0.1,
                    lon = 0.2
                ),
                country = "VN",
                population = 1000,
                timezone = 1,
            ),

            cod = "",
            message = 0.0,
            cnt = 0,
            list = emptyList(),
        )
    }
}