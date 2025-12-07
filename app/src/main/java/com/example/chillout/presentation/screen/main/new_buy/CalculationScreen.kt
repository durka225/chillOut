package com.example.chillout.presentation.screen.main.new_buy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chillout.presentation.screen.main.new_buy.navigation.ScreenTo
import com.example.chillout.presentation.screen.viewmodel.CalculationScreenViewModel
import com.example.chillout.presentation.ui.component.ActionButtonsRow
import com.example.chillout.presentation.ui.component.BuyStatusBlock
import com.example.chillout.presentation.ui.component.PurchasePredictionCard



@Composable
fun CalculationScreen(
    onNavigateTo: (ScreenTo) -> Unit = {},
    viewModel: CalculationScreenViewModel = viewModel()
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 40.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = "Расчет покупки",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            BuyStatusBlock(
                purchaseName = viewModel.purchaseName,
                onDeferClick = {
                    viewModel.toggleDeferred(true)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "${viewModel.coolingPeriodDays} дней",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "срок охлаждения",
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            PurchasePredictionCard(days = viewModel.daysToPurchase)

            Spacer(modifier = Modifier.height(48.dp))

            ActionButtonsRow(
                onBuyNow = {  },
                onCancel = {  }
            )

            Spacer(modifier = Modifier.height(16.dp))


        }
    }
}
@Preview(showBackground = true)
@Composable
fun CalculationScreenPreview() {
    CalculationScreen()
}