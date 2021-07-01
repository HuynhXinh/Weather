package com.example.weather.base

import org.junit.Rule

abstract class BaseTest{
    @get:Rule
    val executorRule = ArchExecutorTestRule()
}