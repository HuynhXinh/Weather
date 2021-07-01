package com.example.domain

import androidx.annotation.VisibleForTesting
import com.example.domain.base.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

abstract class UseCaseTest {
    protected val dispatcherProvider = InstantCoroutineDispatchers()
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
class InstantCoroutineDispatchers : DispatcherProvider {
    override val io: CoroutineDispatcher = TestCoroutineDispatcher()
}