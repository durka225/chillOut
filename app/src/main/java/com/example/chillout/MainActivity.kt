package com.example.chillout

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.chillout.presentation.navigation.MainNav
import com.example.chillout.presentation.ui.theme.ChillOutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChillOutTheme(dynamicColor = false) {
                Surface (modifier = Modifier.fillMaxSize()) {
                    MainContent(
                    )
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier
){
    MainNav(navHostController = rememberNavController(), modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChillOutTheme {
        MainContent()
    }
}