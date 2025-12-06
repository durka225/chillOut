package com.example.chillout.presentation.screen.main.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chillout.presentation.ui.component.AddEditRangeDialog
import com.example.chillout.presentation.ui.component.CardedDropdown
import com.example.chillout.presentation.ui.component.CoolingRangeItem

val coolingRangesState = mutableStateOf(
    listOf(
        CoolingRange(1, 0, 15000, "1", "сутки"),
        CoolingRange(2, 15000, 50000, "1", "неделя"),
        CoolingRange(3, 50000, 100000, "1", "месяц"),
        CoolingRange(4, 100000, null, "3", "месяца") // "и т.д."
    )
)

private var nextRangeId = coolingRangesState.value.maxOfOrNull { it.id } ?: 1

fun addCoolingRange(range: CoolingRange) {
    nextRangeId++
    coolingRangesState.value = coolingRangesState.value + range.copy(id = nextRangeId)
    coolingRangesState.value = coolingRangesState.value.sortedBy { it.minAmount }
}

fun deleteCoolingRange(rangeId: Int) {
    coolingRangesState.value = coolingRangesState.value.filter { it.id != rangeId }
}
@Composable
fun SettingsScreen() {
    val currentRanges = coolingRangesState.value

    var selectedCategory by remember { mutableStateOf("Категория") }
    var pollCount by remember { mutableStateOf("7") }
    var pollPeriod by remember { mutableStateOf("дней") }
    var selectedChannel by remember { mutableStateOf("Telegram") }
    var showRangeDialog by remember { mutableStateOf(false) }

    val periodOptions = listOf("дней", "недель", "месяцев")


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Text(
                text = "Настройки",
                fontSize = 32.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Диапазоны охлаждения",
                    fontSize = 18.sp,
                )
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить диапазон",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { showRangeDialog = true }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(currentRanges) { range ->
            CoolingRangeItem(range = range, onDelete = ::deleteCoolingRange)
        }

        item {
            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
        }
        item {
            Text(
                text = "Черный список категорий",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            CardedDropdown(
                label = "Категория",
                options = listOf("Транспорт", "Электроника", "Одежда"),
                selectedOption = selectedCategory,
                onOptionSelected = { selectedCategory = it }
            )

            Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
        }
        item {
            Text(
                text = "Настройка уведомлений",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Частота опроса",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(end = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    OutlinedTextField(
                        value = pollCount,
                        onValueChange = { pollCount = it.filter { char -> char.isDigit() } },
                        label = { Text("Кол-во") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                }
                Box(modifier = Modifier.weight(0.6f)) {
                    CardedDropdown(
                        label = "Период",
                        options = periodOptions,
                        selectedOption = pollPeriod,
                        onOptionSelected = { pollPeriod = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Канал",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            CardedDropdown(
                label = "Канал",
                options = listOf("Telegram", "Email", "Viber"),
                selectedOption = selectedChannel,
                onOptionSelected = { selectedChannel = it }
            )
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
    if (showRangeDialog) {
        AddEditRangeDialog(
            onDismiss = { showRangeDialog = false },
            onSave = { newRange ->
                addCoolingRange(newRange)
                showRangeDialog = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}