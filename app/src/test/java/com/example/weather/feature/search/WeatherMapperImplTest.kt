package com.example.weather.feature.search

import com.example.domain.feature.*
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Test

/**
 * ~ This is just lazy testing :)))
 * ~ I want to show the approach testing
 */
class WeatherMapperImplTest {
    private val timeFormatter: TimeFormatter = mockkClass(TimeFormatter::class)
    private val weatherMapper = WeatherMapperImpl(timeFormatter)

    @Test
    fun `verify convert CityWeather to list item weather`() {
        // Given
        every { timeFormatter.format(any()) } returns ""

        // When
        val listWeathers = weatherMapper.toItemWeathers(mockCityWeather())

        // Then
        verify(exactly = 7) { timeFormatter.format(any()) }
        MatcherAssert.assertThat(listWeathers.size, `is`(7))
    }

    private fun mockCityWeather(): CityWeather {
        return CityWeather(
            city = City(
                id = 999,
                name = "Sai Gon",
                coord = mockkClass(Coord::class),
                country = "VN",
                population = 1000,
                timezone = 1,
            ),
            cod = "",
            message = 0.0,
            cnt = 0,
            elements = mockElements(),
        )
    }

    private fun mockElements(): List<Element> {
        return listOf(
            createElement(1),
            createElement(2),
            createElement(3),
            createElement(4),
            createElement(5),
            createElement(6),
            createElement(6),
        )
    }

    private fun createElement(dt: Long): Element {
        return Element(
            dt = dt,
            sunrise = 0,
            sunset = 0,
            temp = Temp(
                day = 0.0,
                min = 0.0,
                max = 0.0,
                night = 0.0,
                eve = 0.0,
                morn = 0.0,
            ),
            feelsLike = FeelsLike(
                day = 0.0,
                night = 0.0,
                eve = 0.0,
                morn = 0.0,
            ),
            pressure = 0,
            humidity = 0,
            weather = listOf(
                Weather(
                    id = 0,
                    main = "",
                    description = "",
                    icon = "",
                )
            ),
            speed = 0.0,
            deg = 0,
            gust = 0.0,
            clouds = 0,
            pop = 0.0,
            rain = 0.0,
        )
    }
}