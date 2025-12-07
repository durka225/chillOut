package com.example.chillout.presentation.ui.component

import android.view.Surface
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ActionButtonsRow(onBuyNow: () -> Unit, onCancel: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .height(45.dp)
                .padding(end = 8.dp)
                .clickable(onClick = onBuyNow),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFF66FF66),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "Купить сейчас",
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Surface(
            modifier = Modifier
                .weight(1f)
                .height(45.dp)
                .padding(start = 8.dp)
                .clickable(onClick = onCancel),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Color(0xFFFF6666)),
            color = Color.White
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "Отменить",
                    color = Color(0xFFFF6666),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}