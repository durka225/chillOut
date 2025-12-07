package com.example.chillout.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chillout.presentation.screen.main.settings.CoolingRange

class SettingsScreenViewModel : ViewModel() {
    private var nextRangeId = 5
    var includeCurrentMoney by mutableStateOf(true)
        private set

    var coolingRanges: List<CoolingRange> by mutableStateOf(getInitialCoolingRanges())
        private set

    var selectedCategory: String by mutableStateOf("Категория")
        private set

    var pollCount: String by mutableStateOf("7")
        private set

    var pollPeriod: String by mutableStateOf("дней")
        private set

    var selectedChannel: String by mutableStateOf("Telegram")
        private set

    fun toggleIncludeCurrentMoney() {
        includeCurrentMoney = !includeCurrentMoney
    }

    fun addCoolingRange(range: CoolingRange) {
        if (coolingRanges.any { it.minAmount == range.minAmount && it.maxAmount == range.maxAmount }) return

        nextRangeId++
        val newRange = range.copy(id = nextRangeId)

        coolingRanges = (coolingRanges + newRange).sortedBy { it.minAmount }
    }

    fun deleteCoolingRange(rangeId: Int) {
        coolingRanges = coolingRanges.filter { it.id != rangeId }
    }

    fun updateSelectedCategory(category: String) {
        this.selectedCategory = category
    }

    fun updatePollCount(count: String) {
        this.pollCount = count.filter { it.isDigit() }
    }

    fun updatePollPeriod(period: String) {
        this.pollPeriod = period
    }

    fun updateSelectedChannel(channel: String) {
        this.selectedChannel = channel
    }

    private fun getInitialCoolingRanges(): List<CoolingRange> {
        return listOf(
            CoolingRange(1, 0, 15000, "1", "сутки"),
            CoolingRange(2, 15000, 50000, "1", "неделя"),
            CoolingRange(3, 50000, 100000, "1", "месяц"),
            CoolingRange(4, 100000, null, "3", "месяца")
        )
    }

    fun saveSettings() {
        println("Settings saved: $coolingRanges, $pollCount, $selectedChannel")
    }
}