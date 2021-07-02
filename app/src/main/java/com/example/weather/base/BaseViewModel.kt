package com.example.weather.base

import androidx.lifecycle.ViewModel
import com.example.weather.util.FailureHandler
import com.example.weather.util.SingleLiveEvent

abstract class BaseViewModel(private val failureHandler: FailureHandler) : ViewModel() {
    val error = SingleLiveEvent<String>()
    val loading = SingleLiveEvent<Boolean>()

    protected fun handleFailure(failure: Throwable) {
        error.value = failureHandler.getMsg(failure)
    }
}