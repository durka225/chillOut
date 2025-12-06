package com.example.chillout.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginScreenViewModel : ViewModel() {

    var username by mutableStateOf("")
        private set

    var isUsernameError by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf("")
        private set

    fun updateUsername(newUsername: String) {
        username = newUsername
        isUsernameError = false
        errorMessage = ""
    }

    fun validateUsername(): Boolean {
        if (username.isBlank()) {
            isUsernameError = true
            errorMessage = "Имя пользователя не может быть пустым."
            return false
        }
        if (username.length < 3 || username.length > 20) {
            isUsernameError = true
            errorMessage = "Имя должно содержать от 3 до 20 символов."
            return false
        }
        if (!username.matches(Regex("^[a-zA-Z0-9_]*$"))) {
            isUsernameError = true
            errorMessage = "Используйте только латинские буквы, цифры и знак '_'"
            return false
        }

        isUsernameError = false
        errorMessage = ""
        return true
    }
}