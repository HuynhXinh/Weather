package com.example.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
    val io: CoroutineDispatcher
}

class DispatcherProviderImpl : DispatcherProvider {
    override val io = Dispatchers.IO
}
