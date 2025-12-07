package com.example.chillout.presentation.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chillout.presentation.screen.main.home.Purchase

@Composable
fun NewPurchase(
    purchase: Purchase,
    onBuy : () -> Unit = { },
    onCancel : () -> Unit = { }
) {
    val color = when (purchase.status) {
        "PURCHASED" -> Color(0xFF7BE495)
        "COOLING"  -> Color(0xFF8BD3FF)
        "CANCELED"   -> Color(0xFFFF9B9B)
        else    -> Color.LightGray
    }

    Log.d("HomeScreen", "Rendering status: ${purchase.status}")

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
            verticalAlignment = Alignment.CenterVertically
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

                Text(
                    text = "${purchase.price} ₽",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    purchase.dataLock,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            OutlinedButton(
                onClick = { onBuy() },
                border = null
            ) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Success Icon",
                    tint = Color.Green
                )
            }
            OutlinedButton(
                onClick = { onCancel() },
                border = null
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Success Icon",
                    tint = Color.Red
                )
            }
        }
    }
}