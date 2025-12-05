package com.example.chillout.presentation.screen.main.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.AddCircle
import androidx.compose.material.icons.sharp.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chillout.R

@Composable
fun BottomNavigationBar(
    navController: NavController
){
    val bottomNavItem = listOf(
        BottomNavItem(
            icon = Icons.Outlined.Home,
            titleResId = R.string.home,
            route = MainScreenNavigationRoute.Home
        ),
        BottomNavItem(
            icon = Icons.Sharp.AddCircle,
            titleResId = R.string.history,
            route = MainScreenNavigationRoute.History,
        ),
        BottomNavItem(
            icon = Icons.Sharp.Add,
            titleResId = R.string.new_buy,
            route = MainScreenNavigationRoute.NewBuy,
        ),
        BottomNavItem(
            icon = Icons.Sharp.Call,
            titleResId = R.string.calculator_buy,
            route = MainScreenNavigationRoute.CalculatorBuy,
        ),
        BottomNavItem(
            icon = Icons.Outlined.Settings,
            titleResId = R.string.settings,
            route = MainScreenNavigationRoute.Settings
        )

    )
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        bottomNavItem.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = item.icon,
                        contentDescription = "BottomNavItem icon"
                    )
                },
            )
        }
    }
}
@Composable
@Preview (showBackground = true)
fun MainScreenPreview(){
    BottomNavigationBar(navController = rememberNavController())
}