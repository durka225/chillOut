package com.example.chillout.presentation.screen.main.new_buy.navigation

import com.example.chillout.presentation.screen.main.navigation.MainScreenNavigationRoute
import kotlinx.serialization.Serializable

interface MainScreenNavigationRoute {
    @Serializable
    data object NewBuy: MainScreenNavigationRoute

    @Serializable
    data object Calculation: MainScreenNavigationRoute

}