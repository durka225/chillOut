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
    var coolingPeriodDays by mutableStateOf(0)
        private set
    var daysToPurchase by mutableStateOf(0)
        private set

    var isDeferred by mutableStateOf(false)
        private set

    fun toggleDeferred(isDeferredNow: Boolean) {
        isDeferred = isDeferredNow
        if (isDeferred) {
            daysToPurchase += 30
        }
    }

    fun setPurchaseData(name: String, price: Int, category: String) {
        purchaseName = name
    }

    fun setCoolingPeriod(days: Int) {
        coolingPeriodDays = days
    }

    fun calculateDaysToPurchase(savingMoney: Int, currentMoney: Int, price: Int) {
        val monthlySavings = savingMoney
        if (monthlySavings <= 0) {
            daysToPurchase = -1
            return
        }

        val amountNeeded = price - currentMoney
        if (amountNeeded <= 0) {
            daysToPurchase = 0
            return
        }

        val monthsNeeded = (amountNeeded.toDouble() / monthlySavings).toInt() + 1
        daysToPurchase = monthsNeeded * 30
    }
}