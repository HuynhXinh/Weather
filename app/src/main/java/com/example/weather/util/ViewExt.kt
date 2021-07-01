package com.example.weather.util

import android.view.View

fun View.show(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}