package com.example.chillout.presentation.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chillout.presentation.navigation.Screen
import com.example.chillout.presentation.screen.main.history.HistoryScreen
import com.example.chillout.presentation.screen.main.home.HomeScreen
import com.example.chillout.presentation.screen.main.navigation.BottomNavigationBar
import com.example.chillout.presentation.screen.main.navigation.MainScreenNavigationRoute
import com.example.chillout.presentation.screen.main.new_buy.NewBuyScreen
import com.example.chillout.presentation.screen.main.notification.NotificationScreen
import com.example.chillout.presentation.screen.main.settings.SettingsScreen

@Composable
fun MainScreen (
    onNavigateTo: (Screen) -> Unit = {}
){
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = MainScreenNavigationRoute.Home
        ){
            composable<MainScreenNavigationRoute.Home> {
                HomeScreen()
            }
            composable<MainScreenNavigationRoute.History> {
                HistoryScreen()
            }
            composable<MainScreenNavigationRoute.NewBuy> {
                NewBuyScreen()
            }
            composable<MainScreenNavigationRoute.CalculatorBuy> {
                NotificationScreen()
            }
            composable<MainScreenNavigationRoute.Settings> {
                SettingsScreen()
            }

        }
    }
}
@Composable
@Preview (showBackground = true)
fun MainScreenPreview(){
    MainScreen()
}