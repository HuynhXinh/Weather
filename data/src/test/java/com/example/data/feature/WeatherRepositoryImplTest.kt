package com.example.data.feature

import com.example.domain.feature.CityWeather
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Test

class WeatherRepositoryImplTest {
    private val weatherService: WeatherService = mockkClass(WeatherService::class)
    private val weatherDtoMapper: WeatherDtoMapper = mockkClass(WeatherDtoMapper::class)

    private val weatherRepository = WeatherRepositoryImpl(
        weatherService = weatherService,
        weatherDtoMapper = weatherDtoMapper
    )

    @Test
    fun `should search weather fail`() = runBlockingTest {
        // Given
        every {
            weatherService.searchWeather(any(), any(), any())
        } throws RuntimeException()

        every {
            weatherDtoMapper.toWeather(any())
        } returns mockkClass(CityWeather::class)

        // When
        try {
            weatherRepository.searchWeather("saigon").single()
        } catch (ex: Exception) {
        }

        // Then
        verify(exactly = 1) {
            weatherService.searchWeather(
                query = "saigon",
                cnt = any(),
                appid = any()
            )
        }
        verify(exactly = 0) { weatherDtoMapper.toWeather(any()) }
    }

    @Test
    fun `should search weather success`() = runBlockingTest {
        // Given
        every {
            weatherService.searchWeather(any(), any(), any())
        } returns flowOf(mockCityWeatherDto())

        every {
            weatherDtoMapper.toWeather(any())
        } returns mockkClass(CityWeather::class)

        // When
        weatherRepository.searchWeather("saigon").single()

        // Then
        verify(exactly = 1) {
            weatherService.searchWeather(
                query = "saigon",
                cnt = any(),
                appid = any()
            )
        }
        val captureCityWeatherDto = slot<CityWeatherDto>()
        verify(exactly = 1) { weatherDtoMapper.toWeather(capture(captureCityWeatherDto)) }

        MatcherAssert.assertThat(captureCityWeatherDto.captured.city.id, `is`(999))
    }

    private fun mockCityWeatherDto(): CityWeatherDto {
        return CityWeatherDto(
            city = CityDto(
                id = 999,
                name = "Sai Gon",
                coord = mockkClass(CoordDto::class),
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