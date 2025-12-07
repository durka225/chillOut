package com.example.chillout.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.chillout.R
import com.example.chillout.presentation.screen.main.settings.CoolingRange

@Composable
fun AddEditRangeDialog(
    onDismiss: () -> Unit,
    onSave: (CoolingRange) -> Unit
) {
    var minAmountInput by remember { mutableStateOf("") }
    var maxAmountInput by remember { mutableStateOf("") }
    var durationCount by remember { mutableStateOf("1") }
    var durationUnit by remember { mutableStateOf("сутки") }
    var isMaxUnlimited by remember { mutableStateOf(false) }

    val unitOptions = listOf("сутки", "неделя", "месяц")

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Добавить диапазон", fontSize = 20.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = "Закрыть",
                        tint = Color(0xFFE57373),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(onClick = onDismiss)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    OutlinedTextField(
                        value = minAmountInput,
                        onValueChange = { minAmountInput = it.filter { char -> char.isDigit() } },
                        label = { Text("От суммы (₽)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth().background(Color.White),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (!isMaxUnlimited) {
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        OutlinedTextField(
                            value = maxAmountInput,
                            onValueChange = {
                                maxAmountInput = it.filter { char -> char.isDigit() }
                            },
                            label = { Text("До суммы (₽)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth().background(Color.White),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isMaxUnlimited = !isMaxUnlimited }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier
                        .size(20.dp)
                        .background(if (isMaxUnlimited) Color.Gray else Color.LightGray, RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Без максимальной суммы", fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Длительность охлаждения", modifier = Modifier.padding(bottom = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = durationCount,
                        onValueChange = { durationCount = it.filter { char -> char.isDigit() } },
                        label = { Text("Кол-во") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(0.4f)
                            .padding(end = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Box(modifier = Modifier.weight(0.6f)) {
                        CardedDropdown(
                            label = "Единица",
                            options = unitOptions,
                            selectedOption = durationUnit,
                            onOptionSelected = { durationUnit = it }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                StyledButton(
                    onClick = {
                        val min = minAmountInput.toIntOrNull() ?: 0
                        val max = if (isMaxUnlimited || maxAmountInput.isBlank()) null else maxAmountInput.toIntOrNull()
                        val count = durationCount.ifEmpty { "0" }

                        if (minAmountInput.isNotBlank() && durationCount.isNotBlank() && min <= (max ?: Int.MAX_VALUE)) {
                            onSave(CoolingRange(
                                id = 0,
                                minAmount = min,
                                maxAmount = max,
                                durationCount = count,
                                durationUnit = durationUnit
                            ))
                        }
                    },
                    containerColor = Color(0xFFFFFF11),
                    contentColor = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Сохранить диапазон", fontSize = 19.sp)
                }
            }
        }
    }
}