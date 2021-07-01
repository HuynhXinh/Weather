package com.example.data.feature

import com.example.domain.feature.*

interface WeatherDtoMapper {
    fun toForeCast(dto: ForeCastDto): ForeCast
}

class WeatherDtoMapperImpl : WeatherDtoMapper {
    override fun toForeCast(dto: ForeCastDto): ForeCast {
        return ForeCast(
            city = toCity(dto.city),
            cod = dto.cod,
            message = dto.message,
            cnt = dto.cnt,
            elements = toElements(dto.list),
        )
    }

    private fun toElements(list: List<ListElementDto>): List<Element> {
        return list.map { toElement(it) }
    }

    private fun toElement(it: ListElementDto): Element {
        return Element(
            dt = it.dt,
            sunrise = it.sunrise,
            sunset = it.sunset,
            temp = toTemp(it.temp),
            feelsLike = toFeelsLike(it.feelsLike),
            pressure = it.pressure,
            humidity = it.humidity,
            weather = toWeathers(it.weather),
            speed = it.speed,
            deg = it.deg,
            gust = it.gust,
            clouds = it.clouds,
            pop = it.pop,
            rain = it.rain,
        )
    }

    private fun toWeathers(weather: List<WeatherDto>): List<Weather> {
        return weather.map { toWeather(it) }
    }

    private fun toWeather(it: WeatherDto): Weather {
        return Weather(
            id = it.id,
            main = it.main,
            description = it.description,
            icon = it.icon,
        )
    }

    private fun toFeelsLike(feelsLike: FeelsLikeDto): FeelsLike {
        return FeelsLike(
            day = feelsLike.day,
            night = feelsLike.night,
            eve = feelsLike.eve,
            morn = feelsLike.morn,
        )
    }

    private fun toTemp(temp: TempDto): Temp {
        return Temp(
            day = temp.day,
            min = temp.min,
            max = temp.max,
            night = temp.night,
            eve = temp.eve,
            morn = temp.morn,
        )
    }

    private fun toCity(city: CityDto): City {
        return City(
            id = city.id,
            name = city.name,
            coord = toCoord(city.coord),
            country = city.country,
            population = city.population,
            timezone = city.timezone,
        )
    }

    private fun toCoord(coord: CoordDto): Coord {
        return Coord(
            lat = coord.lat,
            lon = coord.lon
        )
    }
}