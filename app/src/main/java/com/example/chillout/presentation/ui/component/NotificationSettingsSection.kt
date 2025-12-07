package com.example.chillout.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chillout.presentation.screen.viewmodel.NotificationScreenViewModel


@Composable
fun NotificationSettingsSection(viewModel: NotificationScreenViewModel) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Настройка уведомлений",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SettingRow(label = "Частота опроса") {
            CustomDropdown(
                selectedOption = viewModel.selectedFrequency,
                options = viewModel.frequencyOptions,
                onOptionSelected = viewModel::updateSelectedFrequency,
            )
        }

        Spacer(Modifier.height(20.dp))

        SettingRow(label = "Канал") {
            CustomDropdown(
                selectedOption = viewModel.selectedChannel,
                options = viewModel.channelOptions,
                onOptionSelected = viewModel::updateSelectedChannel
            )
        }
    }
}

