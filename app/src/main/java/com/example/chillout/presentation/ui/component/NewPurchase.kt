package com.example.chillout.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun NewPurchase(purchase: Purchase) {

    val color = when (purchase.categoryName.lowercase()) {
        "green" -> Color(0xFF7BE495)
        "blue"  -> Color(0xFF8BD3FF)
        "red"   -> Color(0xFFFF9B9B)
        else    -> Color.LightGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
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
                purchase.datalock,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

//@Composable
//@Preview (showBackground = true)
//fun GoalItemPreview(){
//    GoalItem()
//}