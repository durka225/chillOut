package com.example.chillout.presentation.screen.main.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chillout.R

@Composable
fun BottomNavigationBar(navController: NavController) {

    val items = listOf(
        BottomNavItem(
            iconRes = R.drawable.home,
            title = R.string.home,
            route = MainScreenNavigationRoute.Home,
            iconSize = 24.dp
        ),
        BottomNavItem(
            iconRes = R.drawable.history,
            title = R.string.history,
            route = MainScreenNavigationRoute.History,
            iconSize = 30.dp
        ),
        BottomNavItem(
            vectorIcon = Icons.Sharp.Add,
            title = R.string.new_buy,
            route = MainScreenNavigationRoute.NewBuy,
            iconSize = 32.dp
        ),
        BottomNavItem(
            iconRes = R.drawable.calculate,
            title = R.string.calculator_buy,
            route = MainScreenNavigationRoute.CalculatorBuy,
            iconSize = 24.dp
        ),
        BottomNavItem(
            iconRes = R.drawable.settings,
            title = R.string.settings,
            route = MainScreenNavigationRoute.Settings,
            iconSize = 30.dp
        )
    )


    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = Color.Black,
        tonalElevation = 0.dp
    ) {

        items.forEachIndexed { index, item ->

            val isCenter = index == 2
            val isSelected = selectedIndex == index

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    selectedIndex = index
                    navController.navigate(item.route)
                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Box(
                            modifier = Modifier
                                .height(4.dp)
                                .width(if (isCenter) 48.dp else 40.dp)
                                .background(
                                    if (isSelected) Color(0xFFFFEB3B)
                                    else Color.Transparent
                                )
                        )

                        val size = item.iconSize

                        if (isCenter) {

                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {

                                if (item.iconRes != null) {
                                    Icon(
                                        painter = painterResource(item.iconRes),
                                        tint = Color.Black,
                                        contentDescription = null,
                                        modifier = Modifier.size(size)
                                    )
                                } else {
                                    Icon(
                                        imageVector = item.vectorIcon!!,
                                        tint = Color.Black,
                                        contentDescription = null,
                                        modifier = Modifier.size(size)
                                    )
                                }
                            }

                        } else {

                            if (item.iconRes != null) {
                                Icon(
                                    painter = painterResource(item.iconRes),
                                    tint = Color.White,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .size(size)
                                )
                            } else {
                                Icon(
                                    imageVector = item.vectorIcon!!,
                                    tint = Color.White,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .size(size)
                                )
                            }
                        }
                    }
                }
                ,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
@Composable
@Preview (showBackground = true)
fun MainScreenPreview(){
    BottomNavigationBar(navController = rememberNavController())
}