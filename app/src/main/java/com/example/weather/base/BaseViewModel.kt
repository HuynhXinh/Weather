package com.example.weather.base

import androidx.lifecycle.ViewModel
import com.example.weather.util.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {
    val error = SingleLiveEvent<Throwable>()
    val loading = SingleLiveEvent<Boolean>()
}