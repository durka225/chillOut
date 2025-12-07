package com.example.chillout.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chillout.presentation.screen.main.notification.NotificationItem

class NotificationScreenViewModel : ViewModel() {

    val frequencyOptions = listOf("неделя", "день", "месяц")
    var selectedFrequency by mutableStateOf(frequencyOptions[0])
        private set

    val channelOptions = listOf("Telegram", "Email", "SMS")
    var selectedChannel by mutableStateOf(channelOptions[0])
        private set


    private val initialItems = listOf(
        NotificationItem(id = "1", name = "Машина", isChecked = false),
        NotificationItem(id = "2", name = "Костюм", isChecked = false),
        NotificationItem(id = "3", name = "Шкаф", isChecked = true),
        NotificationItem(id = "4", name = "Ноутбук", isChecked = false)
    )

    var items = mutableStateListOf<NotificationItem>().apply { addAll(initialItems) }
        private set


    fun updateSelectedFrequency(newFrequency: String) {
        selectedFrequency = newFrequency
    }

    fun updateSelectedChannel(newChannel: String) {
        selectedChannel = newChannel
    }

    fun toggleItemChecked(itemId: String) {
        val index = items.indexOfFirst { it.id == itemId }
        if (index != -1) {
            val currentItem = items[index]
            items[index] = currentItem.copy(isChecked = !currentItem.isChecked)
        }
    }
}