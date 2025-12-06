package com.example.chillout.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MoneyCard(title: String, amount: Int) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(title, fontSize = 14.sp, color = Color.Gray)
        Spacer(Modifier.height(20.dp))
        Text(
            modifier = Modifier.height(30.dp),
            text = "%,d ₽".format(amount).replace(',', ' '),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp

        )
    }
}
@Composable
@Preview (showBackground = true)
fun MoneyCardPreview(){
    MoneyCard("Накопления", 15000)
}