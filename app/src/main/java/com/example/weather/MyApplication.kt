package com.example.weather

import androidx.multidex.MultiDexApplication
import com.example.data.di.repositoryModule
import com.example.data.di.serviceModule
import com.example.domain.di.useCaseModule
import com.example.weather.di.appModule
import com.example.weather.di.navigatorModule
import com.example.weather.di.networkModule
import com.example.weather.di.viewModelModule
import com.github.ajalt.timberkt.Timber
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import java.util.*

class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        setupInjection()

        Timber.plant(LoggerManager())
    }

    private fun setupInjection() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                appModule,

                navigatorModule,

                networkModule,

                viewModelModule,

                useCaseModule,

                serviceModule,
                repositoryModule,
            )
        }
    }
}
