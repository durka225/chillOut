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
import androidx.navigation.toRoute

@Serializable
sealed class ScreenTo {
    @Serializable
    data object NewBuy : ScreenTo()

    @Serializable
    data class Calculation(
        val purchaseName: String,
        val price: Int,
        val categoryName: String
    ) : ScreenTo()
}

@Composable
fun MainBuyNav(
    modifier: Modifier = Modifier,
    navHostControllert: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostControllert,
        startDestination = ScreenTo.NewBuy
    ) {
        composable<ScreenTo.NewBuy> {
            NewBuyScreen(
                onNavigateTo = { navigateTo ->
                    navHostControllert.navigate(navigateTo) {
                        popUpTo<ScreenTo.NewBuy> { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<ScreenTo.Calculation> { backStackEntry ->
            val args = backStackEntry.toRoute<ScreenTo.Calculation>()
            CalculationScreen(
                purchaseName = args.purchaseName,
                price = args.price,
                categoryName = args.categoryName,
                onNavigateTo = { navigateTo ->
                    navHostControllert.navigate(navigateTo) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
