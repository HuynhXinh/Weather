package com.example.data.feature

import com.google.gson.annotations.SerializedName

data class CityWeatherDto(
    val city: CityDto,
    val cod: String,
    val message: Double,
    val cnt: Long,
    val list: List<ListElementDto>
)

data class CityDto(
    val id: Long,
    val name: String,
    val coord: CoordDto,
    val country: String,
    val population: Long,
    val timezone: Long
)

data class CoordDto(
    val lon: Double,
    val lat: Double
)

data class ListElementDto(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: TempDto,

    @SerializedName("feels_like")
    val feelsLike: FeelsLikeDto,

    val pressure: Long,
    val humidity: Long,
    val weather: List<WeatherDto>,
    val speed: Double,
    val deg: Long,
    val gust: Double,
    val clouds: Long,
    val pop: Double,
    val rain: Double
)

data class FeelsLikeDto(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class TempDto(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)

data class WeatherDto(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)