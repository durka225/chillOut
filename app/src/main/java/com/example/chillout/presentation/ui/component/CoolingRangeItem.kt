package com.example.chillout.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chillout.presentation.screen.main.settings.CoolingRange

@Composable
fun CoolingRangeItem(range: CoolingRange, onDelete: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val rangeText = if (range.maxAmount != null) {
                "От ${range.minAmount} до ${range.maxAmount} ₽"
            } else {
                "От ${range.minAmount} ₽ и выше"
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = rangeText,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = "Охлаждение: ${range.durationCount} ${range.durationUnit}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Удалить диапазон",
                tint = Color(0xFFE57373),
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onDelete(range.id) }
            )
        }
    }
}