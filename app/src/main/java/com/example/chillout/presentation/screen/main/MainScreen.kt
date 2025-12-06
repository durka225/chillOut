package com.example.chillout.presentation.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chillout.presentation.navigation.Screen
import com.example.chillout.presentation.screen.main.calculator_buy.CalculatorBuyScreen
import com.example.chillout.presentation.screen.main.history.HistoryScreen
import com.example.chillout.presentation.screen.main.home.HomeScreen
import com.example.chillout.presentation.screen.main.home.MoneyItem
import com.example.chillout.presentation.screen.main.home.Purchase
import com.example.chillout.presentation.screen.main.navigation.BottomNavigationBar
import com.example.chillout.presentation.screen.main.navigation.MainScreenNavigationRoute
import com.example.chillout.presentation.screen.main.new_buy.NewBuyScreen
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
                HomeScreen("Андрей", money = MoneyItem(19000,100000), purchases = listOf(
                    Purchase(
                        name = "Костюм",
                        price = 15000,
                        categoryName = "green",
                        datalock = "—"
                    ),
                    Purchase(
                        name = "Машина",
                        price = 1_500_000,
                        categoryName = "blue",
                        datalock = "05.12.2025"
                    ),
                    Purchase(
                        name = "Шкаф",
                        price = 25000,
                        categoryName = "red",
                        datalock = "—"
                    )
                ))
            }
            composable<MainScreenNavigationRoute.History> {
                HistoryScreen()
            }
            composable<MainScreenNavigationRoute.NewBuy> {
                NewBuyScreen()
            }
            composable<MainScreenNavigationRoute.CalculatorBuy> {
                CalculatorBuyScreen()
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