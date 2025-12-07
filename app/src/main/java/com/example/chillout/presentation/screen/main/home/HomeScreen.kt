package com.example.chillout.presentation.screen.main.home

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chillout.App
import com.example.chillout.api.dto.EditCurrentMoney
import com.example.chillout.api.dto.ProfileResponse
import com.example.chillout.presentation.ui.component.AnalyticsDialog
import com.example.chillout.presentation.ui.component.EditCurrentMoneyDialog
import com.example.chillout.presentation.ui.component.GreetingHeader
import com.example.chillout.presentation.ui.component.NewPurchase

val EmptyPurchaseList = emptyList<Purchase>()

@Composable
fun HomeScreen(
) {
    var isProfileRequest by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }

    var response by remember { mutableStateOf<ProfileResponse?>(null) }

    var displayName by remember { mutableStateOf("Загрузка...") }
    var displayMoney by remember {
        mutableStateOf(MoneyItem(savingMoney = 0, currentMoney = 0))
    }
    var purchasesState by remember {
        mutableStateOf(EmptyPurchaseList)
    }

    var showEditDialog by remember { mutableStateOf(false) }
    var showAnalyticsDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        val storedUsername = App.appContext()
            .getSharedPreferences("local_storage", Context.MODE_PRIVATE)
            .getString("username", "") ?: ""

        if (storedUsername.isNotEmpty()) {
            username = storedUsername
        } else {
            displayName = "Гость"
        }
    }

    LaunchedEffect(username, isProfileRequest) {
        if (username.isNotEmpty() && !isProfileRequest) {
            isProfileRequest = true
            getProfile(username = username) { responseServer ->
                response = responseServer
                isProfileRequest = false
            }
        }
    }


    LaunchedEffect(response) {
        response?.let { profile ->
            //Log.d("HomeScreen", "Response received and processing: $profile")

            displayName = profile.name

            displayMoney = MoneyItem(
                savingMoney = profile.currentMoney,
                currentMoney = profile.savingMoney
            )

            purchasesState = profile.purchases

        } ?: run {
            if (username.isNotEmpty() && !isProfileRequest) {
                displayName = "Ошибка загрузки"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            //.background(Color(0xFFF4F4F4))
            .verticalScroll(rememberScrollState())
    ) {
        GreetingHeader(
            name = displayName,
            money = displayMoney,
            onEditClick = { showEditDialog = true },
            onAnalyticsClick = { showAnalyticsDialog = true }
        )

        Spacer(Modifier.height(24.dp))

        if (purchasesState.isEmpty()) {
            Text(
                text = if (displayName == "Загрузка...") "Загрузка покупок..." else "Покупок пока нет. Добавьте первую!",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            Log.d("HomeScreen", "Rendering purchases list $purchasesState")
            //Log.d("HomeScreen", "Displaying ${purchasesState.size} purchases")
            purchasesState.forEach { purchase ->
                if (purchase.status != "CANCELED" && purchase.status != "PURCHASED") {
                    NewPurchase(
                        purchase = purchase,
                        onBuy = { buyPurchase(purchase.uuid, username = username) },
                        onCancel = { cancelPurchase(purchase.uuid, username = username) }
                    )
                }
            }
        }
        if (showEditDialog) {
            EditCurrentMoneyDialog(
                curSum = displayMoney.savingMoney,
                onDismiss = { showEditDialog = false },
                onSave = { newSavingMoney ->
                    updateMoney(
                        username = username,
                        newMoney = newSavingMoney.toInt()
                    )
                    displayMoney = displayMoney.copy(savingMoney = newSavingMoney.toInt())
                    showEditDialog = false
                }
            )
        }

        if (showAnalyticsDialog) {
            AnalyticsDialog(
                profile = response!!,
                onDismiss = { showAnalyticsDialog = false }
            )
        }
    }
}


@Composable
@Preview (showBackground = true)
fun HomeScreenPreview(){
    val dummyPurchases = listOf(
        Purchase(uuid = "123e4567-e89b-12d3-a456-426614174000", name = "Костюм", price = 15000, categoryName = "green", dataLock = "2025-12-12", status = "PURCHASED"),
        Purchase(uuid = "123e4567-e89b-12d3-a456-426614174001", name = "Машина", price = 1_500_000, categoryName = "blue", dataLock = "2025-12-12", status = "PURCHASED"),
    )
}