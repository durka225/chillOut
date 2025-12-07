package com.example.chillout.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val OuterBlueBorder = Color(0xFF87CEEB)
private val ActiveStatusBackground = Color(0xFFB0C4DE).copy(alpha = 0.2f)
private val ActiveStatusText = Color(0xFF007FFF)
private val DeferButtonBorder = Color.Black.copy(alpha = 0.8f)

@Composable
fun BuyStatusBlock(
    purchaseName: String,
    onDeferClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 15.dp
        ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(2.dp, OuterBlueBorder),
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
        ) {

            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .height(80.dp)
                    .background(Color.White),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = purchaseName,
                    color = ActiveStatusText,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(80.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth(0.9f)
                        .clickable(onClick = onDeferClick),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.5.dp, DeferButtonBorder),
                    color = Color.White
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "Отложить",
                            color = Color.Black.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun BuyStatusBlockPreview() {
    Column(Modifier.padding(16.dp)) {
        BuyStatusBlock(
            purchaseName = "Машина",
            onDeferClick = { println("Отложить нажато") }
        )
    }
}