package com.example.chillout.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CalculationScreenViewModel : ViewModel() {

    var purchaseName by mutableStateOf("Машина")
        private set
    var coolingPeriodDays by mutableStateOf(365)
        private set
    var daysToPurchase by mutableStateOf(330)
        private set

    var isDeferred by mutableStateOf(false)
        private set

    fun toggleDeferred(isDeferredNow: Boolean) {
        isDeferred = isDeferredNow
        if (isDeferred) {
            daysToPurchase += 30
        } else {
            daysToPurchase -= 30
        }
    }
    fun loadPurchaseData(purchaseId: String) {
        viewModelScope.launch {
            delay(500)
            purchaseName = "Машина Tesla X"
            coolingPeriodDays = 365
            daysToPurchase = 330
        }
    }
}