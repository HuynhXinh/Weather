package com.example.weather.base

import androidx.lifecycle.Observer
import com.example.weather.util.SingleLiveEvent
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs

inline fun <reified T> mockkLiveData(): SingleLiveEvent<T> {
    val mockLiveData: SingleLiveEvent<T> = mockk()
    val mockObserver = mockk<Observer<T>>()

    every { mockLiveData.observeForever(any()) } just runs

    mockLiveData.observeForever(mockObserver)

    every { mockObserver.onChanged(any()) } just runs

    every { mockLiveData.observe(any(), any()) } just runs

    return mockLiveData
}