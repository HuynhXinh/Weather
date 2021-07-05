package com.example.weather.feature.search

import com.example.domain.feature.*
import com.example.weather.base.BaseTest
import com.example.weather.feature.captureValues
import com.example.weather.util.FailureHandler
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.slot
import io.mockk.verify
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.junit.Test

class WeatherViewModelTest : BaseTest() {
    private val searchWeatherUseCase: SearchWeatherUseCase = mockkClass(SearchWeatherUseCase::class)
    private val weatherMapper: WeatherMapper = mockkClass(WeatherMapper::class)
    private val failureHandler: FailureHandler = mockkClass(FailureHandler::class)

    private val weatherViewModel = WeatherViewModel(
        searchWeatherUseCase = searchWeatherUseCase,
        weatherMapper = weatherMapper,
        failureHandler = failureHandler
    )

    @Test
    fun `should get weather fail`() {
        // Given
        every { searchWeatherUseCase(any(), any(), any()) } answers {
            thirdArg<(Result<CityWeather>) -> Unit>().invoke(Result.failure(RuntimeException()))
        }
        every { failureHandler.getMsg(any()) } returns "mock error msg"

        // When
        val captureLoading = weatherViewModel.loading.captureValues()
        val captureError = weatherViewModel.error.captureValues()
        weatherViewModel.search("saigon")

        // Then
        val captureParam = slot<String>()
        verify(exactly = 1) {
            searchWeatherUseCase(any(), capture(captureParam), any())
        }

        MatcherAssert.assertThat(captureParam.captured, `is`("saigon"))
        MatcherAssert.assertThat(captureLoading[0], `is`(true))
        MatcherAssert.assertThat(captureLoading[1], `is`(false))

        MatcherAssert.assertThat(captureError[0], `is`("mock error msg"))
    }

    @Test
    fun `should get weather success`() {
        // Given
        every { searchWeatherUseCase(any(), any(), any()) } answers {
            thirdArg<(Result<CityWeather>) -> Unit>().invoke(Result.success(mockCityWeather()))
        }
        every { weatherMapper.toItemWeathers(any()) } returns mockItemWeathers()

        // When
        val captureLoading = weatherViewModel.loading.captureValues()
        val captureOnSearchSuccess = weatherViewModel.onSearchSuccess.captureValues()
        weatherViewModel.search("saigon")

        // Then
        val captureParam = slot<String>()
        verify(exactly = 1) {
            searchWeatherUseCase(any(), capture(captureParam), any())
        }

        val captureCityWeather = slot<CityWeather>()
        verify(exactly = 1) {
            weatherMapper.toItemWeathers(capture(captureCityWeather))
        }

        MatcherAssert.assertThat(captureParam.captured, `is`("saigon"))

        MatcherAssert.assertThat(captureLoading[0], `is`(true))
        MatcherAssert.assertThat(captureLoading[1], `is`(false))

        MatcherAssert.assertThat(captureOnSearchSuccess[0], `is`(notNullValue()))

        MatcherAssert.assertThat(captureCityWeather.captured.city.id, `is`(999))
    }

    private fun mockItemWeathers(): List<ItemWeather> {
        return emptyList()
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
            elements = emptyList(),
        )
    }
}