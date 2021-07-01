package com.example.data.feature

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("2.5/forecast/daily")
    fun searchForeCast(
        @Query("q") query: String?,
        @Query("cnt") cnt: String,
        @Query("appid") appid: String,
    ): Flow<ForeCastDto>
}
