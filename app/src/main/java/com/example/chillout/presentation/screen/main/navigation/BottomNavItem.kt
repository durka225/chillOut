package com.example.chillout.presentation.screen.main.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class BottomNavItem(
    val iconRes: Int? = null,
    val vectorIcon: ImageVector? = null,
    val title: Int,
    val route: MainScreenNavigationRoute,
    val iconSize: Dp = 28.dp
)

