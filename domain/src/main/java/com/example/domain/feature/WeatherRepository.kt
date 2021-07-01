package com.example.domain.feature

import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun searchForeCast(query: String): Flow<Result<ForeCast>>
}