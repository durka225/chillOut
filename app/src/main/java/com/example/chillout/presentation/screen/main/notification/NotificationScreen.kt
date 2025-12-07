package com.example.chillout.presentation.screen.main.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chillout.presentation.screen.viewmodel.NotificationScreenViewModel
import com.example.chillout.presentation.ui.component.NotificationExclusionSection
import com.example.chillout.presentation.ui.component.NotificationSettingsSection

@Composable
fun NotificationScreen(
    viewModel: NotificationScreenViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F6FC))
            .padding(vertical = 16.dp, horizontal = 12.dp)
    ) {
        Text(
            text = "Уведомления",
            fontSize = 32.sp,
            modifier = Modifier.padding(start = 16.dp,  bottom = 24.dp)
        )
        NotificationSettingsSection(viewModel)

        Spacer(Modifier.height(32.dp))

        NotificationExclusionSection(viewModel)
    }
}
@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    NotificationScreen()
}