package com.example.chillout.presentation.screen.main.new_buy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chillout.presentation.screen.login.LoginScreen
import com.example.chillout.presentation.screen.main.MainScreen
import com.example.chillout.presentation.screen.main.new_buy.CalculationScreen
import com.example.chillout.presentation.screen.main.new_buy.NewBuyScreen
import com.example.chillout.presentation.screen.user_profile_setup.UserProfileSetupScreen
import kotlinx.serialization.Serializable

sealed class ScreenTo {
    @Serializable
    data object NewBuy: ScreenTo()

    @Serializable
    data object  Calculation: ScreenTo()

}

@Composable
fun MainBuyNav(
    modifier: Modifier = Modifier,
    navHostControllert: NavHostController
){
    NavHost(
        modifier = modifier,
        navController = navHostControllert,
        startDestination = ScreenTo.NewBuy
    ){
        composable <ScreenTo.NewBuy> {
            NewBuyScreen(
                onNavigateTo = { navigateTo ->
                    navHostControllert.navigate(navigateTo)
                }
            )
        }
        composable <ScreenTo.Calculation> {
            CalculationScreen(
                onNavigateTo = { navigateTo ->
                    navHostControllert.navigate(navigateTo)
                }
            )
        }
    }
}
