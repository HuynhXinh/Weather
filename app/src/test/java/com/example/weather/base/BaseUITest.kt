package com.example.weather.base

import androidx.annotation.CallSuper
import androidx.test.core.app.ApplicationProvider
import com.example.weather.util.SingleLiveEvent
import org.junit.After
import org.junit.Before
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.robolectric.annotation.Config

@Config(application = MyApplicationTest::class)
abstract class BaseUITest {

    private val appTest: MyApplicationTest = ApplicationProvider.getApplicationContext()

    private var mockModules = emptyList<Module>()

    protected val loading: SingleLiveEvent<Boolean> = mockkLiveData()
    protected val error: SingleLiveEvent<String> = mockkLiveData()

    open fun mockModules(): List<Module> = emptyList()

    @Before
    @CallSuper
    open fun setup() {
        mockModules = mockModules()
        loadKoinModules(mockModules)
    }

    @After
    @CallSuper
    fun tearDown() {
        unloadKoinModules(mockModules)
    }


}