package com.example.chillout.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chillout.presentation.screen.main.home.HomeScreen
import com.example.chillout.presentation.screen.main.home.MoneyItem

@Composable
fun GreetingHeader(
    name: String,
    money: MoneyItem,
    onEditClick: () -> Unit = { },
    onAnalyticsClick: () -> Unit = { }
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(
            bottomStart = 50.dp,
            bottomEnd = 50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFFFFF11),
                    shape = RoundedCornerShape(
                        bottomStart = 50.dp,
                        bottomEnd = 50.dp
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "Привет, $name!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(Modifier.height(30.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                    MoneyCard(title = "Накопления", amount = money.savingMoney, onClick = onEditClick)
                    MoneyCard(title = "Сэкономлено", amount = money.currentMoney, onClick = onAnalyticsClick)
                }
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

