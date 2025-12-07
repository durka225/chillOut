package com.example.chillout.presentation.screen.viewmodel

import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chillout.App
import com.example.chillout.presentation.screen.main.history.PurchaseHistory
import com.example.chillout.presentation.screen.main.history.getInitialHistoryPurchases

class HistoryViewModel : ViewModel() {
    var purchases: List<PurchaseHistory> by mutableStateOf(getInitialHistoryPurchases())
        private set

    fun deletePurchase(purchaseId: String) {
        val username = App.appContext().getSharedPreferences("local_storage", Context.MODE_PRIVATE)
            .getString("username", null)!!

        com.example.chillout.presentation.screen.main.history.deletePurchase(purchaseId, username)

//        purchases = purchases.filter { it.id != purchaseId }
//        println("Удалена покупка с ID: $purchaseId")
    }
}