package com.example.weather.util

import android.content.Context

interface StringRes {
    fun getString(@androidx.annotation.StringRes res: Int): String
}

class StringResImpl(private val context: Context) : StringRes {
    override fun getString(res: Int): String {
        return context.getString(res)
    }
}