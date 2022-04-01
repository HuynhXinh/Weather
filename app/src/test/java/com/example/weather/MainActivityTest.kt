package com.example.weather


import com.example.weather.base.BaseUITest
import com.example.weather.base.mockkLiveData
import com.example.weather.feature.search.ItemWeather
import com.example.weather.feature.search.SearchWeatherNavigator
import com.example.weather.feature.search.WeatherViewModel
import com.example.weather.util.SingleLiveEvent
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.module.Module
import org.koin.dsl.module
import org.robolectric.Robolectric.buildActivity
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [21])
class MainActivityTest : BaseUITest() {

    private val searchWeatherNavigator = mockkClass(SearchWeatherNavigator::class)
    private val weatherViewModel = mockkClass(WeatherViewModel::class)

    private val onSearchSuccess: SingleLiveEvent<List<ItemWeather>> = mockkLiveData()

    override fun mockModules(): List<Module> {
        return listOf(
            module {
                single { searchWeatherNavigator }

                single { weatherViewModel }
            }
        )
    }

    @Test
    fun `should call navigator binding`() {
        // GIVEN
        every { searchWeatherNavigator.bind(any()) } returns Unit
        every { weatherViewModel.loading } returns loading
        every { weatherViewModel.error } returns error
        every { weatherViewModel.onSearchSuccess } returns onSearchSuccess

        // WHEN
        buildActivity(MainActivity::class.java).setup().get()

        // THEN
        verify(exactly = 1) { searchWeatherNavigator.bind(any()) }
    }
}