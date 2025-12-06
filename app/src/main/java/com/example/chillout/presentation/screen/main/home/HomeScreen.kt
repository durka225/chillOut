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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chillout.App
import com.example.chillout.api.dto.ProfileResponce
import com.example.chillout.presentation.ui.component.GreetingHeader
import com.example.chillout.presentation.ui.component.NewPurchase

val EmptyPurchaseList = emptyList<Purchase>()

@Composable
fun HomeScreen(
) {
    var isProfileRequest by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }

    var response by remember { mutableStateOf<ProfileResponce?>(null) }

    var displayName by remember { mutableStateOf("Загрузка...") }
    var displayMoney by remember {
        mutableStateOf(MoneyItem(savingMoney = 0, currentMoney = 0))
    }
    var purchasesState by remember {
        mutableStateOf(EmptyPurchaseList)
    }


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
            Log.d("HomeScreen", "Response received and processing: $profile")

            displayName = profile.name

            displayMoney = MoneyItem(
                savingMoney = profile.savingMoney,
                currentMoney = profile.currentMoney
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
            .background(Color(0xFFF4F4F4))
            .verticalScroll(rememberScrollState())
    ) {
        GreetingHeader(name = displayName, money = displayMoney)

        Spacer(Modifier.height(24.dp))

        if (purchasesState.isEmpty()) {
            Text(
                text = if (displayName == "Загрузка...") "Загрузка покупок..." else "Покупок пока нет. Добавьте первую!",
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            purchasesState.forEach { purchase ->
                NewPurchase(purchase = purchase)
            }
        }
    }
}


@Composable
@Preview (showBackground = true)
fun HomeScreenPreview(){
    val dummyPurchases = listOf(
        Purchase(name = "Костюм", price = 15000, categoryName = "green", datalock = "—"),
        Purchase(name = "Машина", price = 1_500_000, categoryName = "blue", datalock = "05.12.2025")
    )
}