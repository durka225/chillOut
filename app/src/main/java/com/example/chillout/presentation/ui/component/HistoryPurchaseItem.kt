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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chillout.presentation.screen.main.history.deletePurchase
import com.example.chillout.presentation.screen.main.home.Purchase

@Composable
fun HistoryPurchaseItem(
    purchase: Purchase,
    onDelete: (String) -> Unit
) {
    val color = when (purchase.status) {
        "PURCHASED" -> Color(0xFF7BE495)
        "COOLING"  -> Color(0xFF8BD3FF)
        "CANCELED"   -> Color(0xFFFF9B9B)
        else    -> Color.LightGray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(color)
            )

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    purchase.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${purchase.price} ₽",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )

                    if (purchase.dataLock != "—") {
                        Spacer(Modifier.width(8.dp))
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF8BD3FF).copy(alpha = 0.5f)
                            ),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "365 дней",
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    purchase.dataLock,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Удалить",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onDelete(purchase.uuid)
                        }
                )
            }
        }
    }
}