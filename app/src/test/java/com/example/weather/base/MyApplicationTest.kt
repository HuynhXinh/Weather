package com.example.weather.base

import androidx.annotation.VisibleForTesting
import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
class MyApplicationTest : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplicationTest)
            modules(emptyList())
        }
    }
}