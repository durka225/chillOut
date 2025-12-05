package com.example.chillout.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginScreenViewModel: ViewModel () {
    var username by mutableStateOf("")
        private set

    fun updateUsername(username: String){
        this.username = username
    }
}