package com.example.chillout.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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