package com.example.chillout.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.chillout.R
import com.example.chillout.api.dto.ProfileResponse

@Composable
fun AnalyticsDialog(
    profile: ProfileResponse,
    onDismiss: () -> Unit
) {
    // Группировка покупок по статусам и суммирование
    val purchasedSum = profile.purchases.filter { it.status == "PURCHASED" }.sumOf { it.price }
    val coolingSum = profile.purchases.filter { it.status == "COOLING" }.sumOf { it.price }
    val canceledSum = profile.purchases.filter { it.status == "CANCELED" }.sumOf { it.price }

    // Алгоритм аналитики: расчет метрик
    val totalIncome = profile.wages
    val totalExpenses = purchasedSum + profile.postpone
    val possibleExpenses = coolingSum
    val canceledExpenses = canceledSum
    val savings = profile.savingMoney
    val economy = totalIncome - totalExpenses

    // Генерация совета на основе условий
    val advice = when {
        economy < 0 -> "Ваши расходы превышают доходы. Попробуйте сократить ненужные траты."
        possibleExpenses > totalIncome * 0.2 -> "Возможные покупки высоки. Рассмотрите приоритеты."
        else -> "Отлично! Вы хорошо управляете финансами. Продолжайте в том же духе."
    }

    // Определение цвета фона и текста для совета
    val (adviceBackgroundColor, adviceTextColor) = when {
        economy < 0 -> Color(0xFFFFEBEE) to Color(0xFFC62828) // Светло-красный фон, темный красный текст
        possibleExpenses > totalIncome * 0.2 -> Color(0xFFFFF8E1) to Color(0xFFF57C00) // Светло-желтый фон, оранжевый текст
        else -> Color(0xFFE8F5E8) to Color(0xFF2E7D32) // Светло-зеленый фон, темный зеленый текст
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = "Закрыть",
                        tint = Color(0xFFE57373),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(onClick = onDismiss)
                    )
                }
                Text(text = "Аналитика для ${profile.name}", fontSize = 20.sp, color = Color(0xFF333333))
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "Общий доход: $totalIncome",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp
                    )
                }
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "Общие расходы: $totalExpenses",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp
                    )
                }
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "Возможные расходы: $possibleExpenses",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp
                    )
                }
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "Отменённые расходы: $canceledExpenses",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp
                    )
                }
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "Сбережения: $savings",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp
                    )
                }
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "Экономия: $economy",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp
                    )
                }
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = adviceBackgroundColor)
                ) {
                    Text(
                        text = "Совет: $advice",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 16.sp,
                        color = adviceTextColor
                    )
                }
            }
        }
    }
}
