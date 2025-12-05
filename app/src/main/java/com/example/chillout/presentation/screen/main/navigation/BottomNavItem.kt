package com.example.chillout.presentation.screen.main.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val icon: ImageVector,
    val titleResId: Int,
    val route: MainScreenNavigationRoute
)
