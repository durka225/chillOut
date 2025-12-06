package com.example.chillout.presentation.screen.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chillout.presentation.ui.component.GreetingHeader
import com.example.chillout.presentation.ui.component.NewPurchase

@Composable
fun HomeScreen(
    name: String = "Андрей",
    money: MoneyItem,
    purchases: List<Purchase>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4))
            .verticalScroll(rememberScrollState())
    ) {
        GreetingHeader(name = name, money = money)

        Spacer(Modifier.height(24.dp))

        purchases.forEach { purchase ->
            NewPurchase(purchase = purchase)
        }
    }
}


@Composable
@Preview (showBackground = true)
fun HomeScreenPreview(){
    HomeScreen(
        name = "Андрей",
        money = MoneyItem(
            savingMoney = 50000,
            currentMoney = 150000
        ),
        purchases = listOf(
            Purchase(
                name = "Костюм",
                price = 15000,
                categoryName = "green",
                datalock = "—",
            ),
            Purchase(
                name = "Машина",
                price = 1_500_000,
                categoryName = "blue",
                datalock = "05.12.2025",
            ),
            Purchase(
                name = "Шкаф",
                price = 25000,
                categoryName = "red",
                datalock = "—",
            )
        )
    )
}