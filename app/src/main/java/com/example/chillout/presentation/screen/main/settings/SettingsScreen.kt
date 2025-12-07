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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chillout.presentation.screen.viewmodel.SettingsScreenViewModel
import com.example.chillout.presentation.ui.component.AddEditRangeDialog
import com.example.chillout.presentation.ui.component.CardedDropdown
import com.example.chillout.presentation.ui.component.CoolingRangeItem


@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel = viewModel()
) {
    var showRangeDialog by remember { mutableStateOf(false) }
    val periodOptions = listOf("дней", "недель", "месяцев")
    val categoryOptions = listOf("Транспорт", "Электроника", "Одежда")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
        items(viewModel.coolingRanges) { range ->
            CoolingRangeItem(range = range, onDelete = viewModel::deleteCoolingRange)
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
                options = categoryOptions,
                selectedOption = viewModel.selectedCategory,
                onOptionSelected = viewModel::updateSelectedCategory
            )

        }

    }

    if (showRangeDialog) {
        AddEditRangeDialog(
            onDismiss = { showRangeDialog = false },
            onSave = { newRange ->
                viewModel.addCoolingRange(newRange)
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
