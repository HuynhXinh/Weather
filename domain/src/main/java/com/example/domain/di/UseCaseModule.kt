package com.example.domain.di

import com.example.domain.base.DispatcherProvider
import com.example.domain.base.DispatcherProviderImpl
import com.example.domain.feature.SearchForeCastUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<DispatcherProvider> { DispatcherProviderImpl() }

    factory { val searchUseCase =
        SearchForeCastUseCase(dispatcherProvider = get(), weatherRepository = get())
        searchUseCase
    }
}