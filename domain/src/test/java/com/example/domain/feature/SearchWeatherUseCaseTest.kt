package com.example.domain.feature

import com.example.domain.UseCaseTest
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class SearchWeatherUseCaseTest : UseCaseTest() {
    private val weatherRepository: WeatherRepository = mockkClass(WeatherRepository::class)
    private val searchWeatherUseCase = SearchWeatherUseCase(
        dispatcherProvider = dispatcherProvider,
        weatherRepository = weatherRepository
    )

    @Test
    fun `should search weather fail`() = runBlockingTest {
        // Given

        // When
        searchWeatherUseCase(scope = this, params = "saigon")

        // Then
        verify(exactly = 1) { weatherRepository.searchWeather("saigon") }
    }
}