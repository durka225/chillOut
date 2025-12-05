package com.example.chillout.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable

internal fun StyledButton (
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    containerColor: Color = Color.Black,
    contentColor: Color = Color.White,
    content: @Composable () -> Unit

){
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp,
            focusedElevation = 10.dp,
            hoveredElevation = 6.dp,
            disabledElevation = 0.dp
        )
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        ){
            content()
        }
    }
}