package com.example.chillout.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UserProfileSetupScreenViewModel : ViewModel() {

    var name by mutableStateOf("")
        private set
    var wages by mutableStateOf("")
        private set
    var savingMoney by mutableStateOf("")
        private set
    var currentMoney by mutableStateOf("")
        private set

    var isNameError by mutableStateOf(false)
        private set
    var isWagesError by mutableStateOf(false)
        private set
    var isSavingMoneyError by mutableStateOf(false)
        private set
    var isCurrentMoneyError by mutableStateOf(false)
        private set

    private fun clearErrorState(field: String) {
        when (field) {
            "name" -> isNameError = false
            "wages" -> isWagesError = false
            "savingMoney" -> isSavingMoneyError = false
            "currentMoney" -> isCurrentMoneyError = false
        }
    }

    fun updateName(newValue: String) {
        name = newValue
        clearErrorState("name")
    }

    fun updateWages(newValue: String) {
        wages = newValue.filter { it.isDigit() }
        clearErrorState("wages")
    }

    fun updateSavingMoney(newValue: String) {
        savingMoney = newValue.filter { it.isDigit() }
        clearErrorState("savingMoney")
    }

    fun updateCurrentMoney(newValue: String) {
        currentMoney = newValue.filter { it.isDigit() }
        clearErrorState("currentMoney")
    }

    fun validateInputs(): Boolean {
        var isValid = true

        if (name.isBlank()) {
            isNameError = true
            isValid = false
        }

        val wagesInt = wages.toIntOrNull()
        if (wagesInt == null || wagesInt <= 0) {
            isWagesError = true
            isValid = false
        }

        val savingMoneyInt = savingMoney.toIntOrNull()
        if (savingMoneyInt == null || savingMoneyInt < 0) {
            isSavingMoneyError = true
            isValid = false
        }

        val currentMoneyInt = currentMoney.toIntOrNull()
        if (currentMoneyInt == null || currentMoneyInt < 0) {
            isCurrentMoneyError = true
            isValid = false
        }

        return isValid
    }
}
