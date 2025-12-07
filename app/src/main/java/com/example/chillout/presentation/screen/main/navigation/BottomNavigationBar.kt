package com.example.chillout.presentation.screen.main.navigation

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.chillout.R
val OnPrimaryColor = Color.White
val BackgroundColor = Color(0xFF1E1E1E)


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(
            iconRes = R.drawable.home,
            title = R.string.home,
            route = MainScreenNavigationRoute.Home,
            iconSize = 27.dp
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
            iconRes = R.drawable.notif,
            title = R.string.notif,
            route = MainScreenNavigationRoute.CalculatorBuy,
            iconSize = 30.dp
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
        containerColor = BackgroundColor,
        tonalElevation = 8.dp,
        modifier = Modifier.height(72.dp),
    ) {
        items.forEachIndexed { index, item ->
            val isCenter = index == 2
            val isSelected = selectedIndex == index

            val scale by animateFloatAsState(
                targetValue = if (isSelected && !isCenter) 1.2f else 1.0f,
                animationSpec = tween(durationMillis = 300)
            )

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    selectedIndex = index
                    navController.navigate(item.route)
                },
                icon = {
                    if (isCenter) {
                        CenterNavigationItem(item = item, isSelected = isSelected)
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = if (item.iconRes != null) painterResource(item.iconRes) else painterResource(R.drawable.home),
                                contentDescription = null,
                                tint = if (isSelected) Color(0xFFFFFF11) else Color.White.copy(alpha = 0.6f),
                                modifier = Modifier
                                    .scale(scale)
                                    .size(item.iconSize)
                            )
                            Spacer(Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) Color(0xFFFFFF11) else Color.Transparent)
                            )
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFFFF11),
                    unselectedIconColor = OnPrimaryColor.copy(alpha = 0.6f),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun CenterNavigationItem(item: BottomNavItem, isSelected: Boolean) {
    val pulse by rememberInfiniteTransition(label = "Pulse").animateFloat(
        initialValue = 1.0f,
        targetValue = if (isSelected) 1.15f else 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ), label = "Pulse Animation"
    )

    val buttonColor = if (isSelected) Color(0xFFFFFF11) else Color.White
    val iconColor = if (isSelected) BackgroundColor else Color.Black

    Box(
        modifier = Modifier
            .offset(y = (2).dp)
            .size(64.dp)
            .clip(CircleShape)
            .scale(pulse)
            .background(buttonColor),

        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = item.vectorIcon!!,
            tint = iconColor,
            contentDescription = null,
            modifier = Modifier.size(item.iconSize)
        )

    }
}

@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    BottomNavigationBar(navController = rememberNavController())
}