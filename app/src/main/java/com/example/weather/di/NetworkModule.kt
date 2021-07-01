package com.example.weather.di

import android.content.Context
import com.example.weather.network.*
import com.example.weather.network.NetworkConfigs.Keys.ACCEPT
import com.example.weather.network.NetworkConfigs.Keys.ACCEPT_LANGUAGE
import com.example.weather.network.NetworkConfigs.Keys.CONTENT_TYPE
import com.example.weather.network.NetworkConfigs.Keys.USER_AGENT
import com.example.weather.network.NetworkConfigs.Values.ACCEPT_VALUE
import com.example.weather.network.NetworkConfigs.Values.TIME_OUT
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        createRetrofit(
            okHttpClient = get(),
            convertFactory = get(),
            flowFactory = get(),
            networkConfigs = get(),
        )
    }

    single {
        provideOkHttpClient(
            cache = provideCache(context = androidApplication()),
            interceptors = provideInterceptors(networkConfigs = get())
        )
    }

    single<NetworkConfigs> { NetworkConfigsImpl() }

    single<Converter.Factory> {
        GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .create()
        )
    }

    single<CallAdapter.Factory> {
        FlowCallAdapterFactory(networkConnection = get())
    }

    single<NetworkConnection> { NetworkConnectionImpl(context = androidApplication()) }
}

private fun createRetrofit(
    okHttpClient: OkHttpClient,
    convertFactory: Converter.Factory,
    flowFactory: CallAdapter.Factory,
    networkConfigs: NetworkConfigs
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(networkConfigs.baseUrl)
        .addConverterFactory(convertFactory)
        .addCallAdapterFactory(flowFactory)
        .client(okHttpClient)
        .build()
}

private fun provideOkHttpClient(
    interceptors: List<Interceptor>,
    cache: Cache
): OkHttpClient {
    return OkHttpClient.Builder().apply {

        interceptors.forEach {
            addInterceptor(it)
        }

        connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        readTimeout(TIME_OUT, TimeUnit.SECONDS)

        cache(cache)
    }.build()
}

private fun provideInterceptors(networkConfigs: NetworkConfigs): List<Interceptor> {
    return listOf(
        provideHeaderInterceptor(networkConfigs),
        provideLoggingInterceptor(networkConfigs)
    )
}

private fun provideLoggingInterceptor(networkConfigs: NetworkConfigs): Interceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = if (networkConfigs.isLogging) HttpLoggingInterceptor.Level.BODY
    else HttpLoggingInterceptor.Level.NONE
    return logging
}

private fun provideHeaderInterceptor(networkConfigs: NetworkConfigs): Interceptor {
    return Interceptor { chain ->
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .addHeader(CONTENT_TYPE, ACCEPT_VALUE)
//            .addHeader(ACCEPT, ACCEPT_VALUE)
//            .addHeader(ACCEPT_LANGUAGE, networkConfigs.acceptLanguage)
//            .addHeader(USER_AGENT, networkConfigs.userAgent)
        chain.proceed(requestBuilder.build())
    }
}

private fun provideCache(context: Context): Cache {
    val size = (10 * 1024 * 1024).toLong() // 10 Mb
    return Cache(context.cacheDir, size)
}
