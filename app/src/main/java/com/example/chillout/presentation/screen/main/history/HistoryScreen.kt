package com.example.chillout.presentation.screen.main.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chillout.presentation.screen.viewmodel.HistoryViewModel
import com.example.chillout.presentation.ui.component.HistoryPurchaseItem

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel()
) {
    val purchases = viewModel.purchases

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "История",
            fontSize = 32.sp,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 16.dp)
        )

        purchases.forEach { purchase ->
            HistoryPurchaseItem(
                purchase = purchase,
                onDelete = viewModel::deletePurchase
            )
        }

        Spacer(Modifier.height(80.dp))
    }
}

@Composable
@Preview (showBackground = true)
fun HistoryScreenPreview(){
    HistoryScreen()
}