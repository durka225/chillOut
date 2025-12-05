package com.example.chillout.presentation.screen.main.navigation

import kotlinx.serialization.Serializable

interface MainScreenNavigationRoute {
        @Serializable
        data object Home: MainScreenNavigationRoute

        @Serializable
        data object History: MainScreenNavigationRoute

        @Serializable
        data object NewBuy: MainScreenNavigationRoute

        @Serializable
        data object CalculatorBuy: MainScreenNavigationRoute

        @Serializable
        data object Settings: MainScreenNavigationRoute

}