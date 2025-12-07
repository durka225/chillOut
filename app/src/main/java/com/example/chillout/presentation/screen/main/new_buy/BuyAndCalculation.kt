package com.example.chillout.presentation.screen.main.new_buy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.chillout.presentation.screen.main.new_buy.navigation.MainBuyNav

@Composable
fun BuyAndCalculation(){
    val navHostControllert = rememberNavController()
    MainContent(navHostControllert = navHostControllert)
}


@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    navHostControllert: NavHostController
){
    MainBuyNav(navHostControllert = navHostControllert, modifier = modifier)
}