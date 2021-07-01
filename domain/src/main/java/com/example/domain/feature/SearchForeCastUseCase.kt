package com.example.domain.feature

import com.example.domain.base.DispatcherProvider
import com.example.domain.base.UseCase
import kotlinx.coroutines.flow.Flow

class SearchForeCastUseCase(
    override val dispatcherProvider: DispatcherProvider,
    private val weatherRepository: WeatherRepository
) : UseCase<ForeCast, String> {
    override fun run(params: String): Flow<Result<ForeCast>> {
        return weatherRepository.searchForeCast(query = params)
    }
}