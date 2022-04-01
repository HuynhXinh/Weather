package com.example.data.feature

import com.example.domain.feature.CityWeather
import java.util.*

interface MemCache {
    fun getOrNull(key: String): CityWeather?

    fun cache(key: String, cityWeather: CityWeather)

    fun clear()
}

class MemCacheImpl : MemCache {
    private val weakHashMap = WeakHashMap<String, CityWeather>()

    override fun getOrNull(key: String): CityWeather? {
        return weakHashMap[key]
    }

    override fun cache(key: String, cityWeather: CityWeather) {
        weakHashMap[key] = cityWeather
    }

    override fun clear() {
        weakHashMap.clear()
    }
}