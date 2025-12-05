package com.example.chillout.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UserProfileSetupScreenViewModel: ViewModel () {
    var name by mutableStateOf("")
        private set

    var wages by mutableStateOf("")
        private set

    var savingMoney by mutableStateOf("")
        private set

    var currentMoney by mutableStateOf("")
        private set

    fun updateName(name: String){
        this.name = name
    }
    fun updateWages (wages: String){
        this.wages = wages
    }
    fun updateSavingMoney(savingMoney: String){
        this.savingMoney = savingMoney
    }
    fun updateCurrentMoney(currentMoney: String){
        this.currentMoney = currentMoney
    }
}
