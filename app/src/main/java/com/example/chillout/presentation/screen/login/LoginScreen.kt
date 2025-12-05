package com.example.chillout.presentation.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chillout.presentation.navigation.Screen
import com.example.chillout.presentation.screen.viewmodel.LoginScreenViewModel

@Composable
fun LoginScreen(
    onNavigateTo: (Screen) -> Unit = {},
    viewModel: LoginScreenViewModel = viewModel()
){

}
@Composable
@Preview (showBackground = true)
fun LoginScreenPreview(){
    LoginScreen()
}