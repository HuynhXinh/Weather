package com.example.weather.navigator

import androidx.navigation.NavController

interface BaseNavigator {

    fun bind(navController: NavController)
}

open class BaseNavigatorImpl : BaseNavigator {

    private var navController: NavController? = null

    override fun bind(navController: NavController) {
        this.navController = navController
    }

    protected fun requireNavigator(): NavController {
        return requireNotNull(navController, { "NavController is required" })
    }
}
