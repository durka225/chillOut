package com.example.chillout.presentation.screen.main.history

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chillout.App
import com.example.chillout.presentation.screen.main.home.Purchase
import com.example.chillout.presentation.screen.viewmodel.HistoryViewModel
import com.example.chillout.presentation.ui.component.HistoryPurchaseItem

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel()
) {
    var purchases = viewModel.purchases

    var username by remember { mutableStateOf("") }

    var isHistoryRequest by remember { mutableStateOf(false) }

    var history by remember { mutableStateOf<List<Purchase>>(emptyList()) }

    var shouldFetchHistory by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val storedUsername = App.appContext()
            .getSharedPreferences("local_storage", Context.MODE_PRIVATE)
            .getString("username", "") ?: ""

        if (storedUsername.isNotEmpty()) {
            username = storedUsername
        } else {
            username = "Гость"
        }
    }

    LaunchedEffect(username, shouldFetchHistory) {
        if (username.isNotEmpty() && shouldFetchHistory) {
            shouldFetchHistory = false
            historyRequest(username) { responseServer ->
                history = responseServer
            }
        }
    }

    LaunchedEffect(isHistoryRequest) {
        if (history.isEmpty()) {
            isHistoryRequest = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Log.d("HistoryScreen", "Response $history")
        Text(
            text = "История",
            fontSize = 32.sp,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 16.dp)
        )

        history.forEach { purchase ->
            if (purchase.status != "COOLING") {
                HistoryPurchaseItem(
                    purchase = purchase,
                    onDelete = {
                        viewModel.deletePurchase(it)
                        shouldFetchHistory = true
                    }
                )
            }
        }
        Spacer(Modifier.height(80.dp))
    }
}

@Composable
@Preview (showBackground = true)
fun HistoryScreenPreview(){
    HistoryScreen()
}