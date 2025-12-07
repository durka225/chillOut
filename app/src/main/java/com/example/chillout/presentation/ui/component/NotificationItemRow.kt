package com.example.chillout.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chillout.presentation.screen.main.notification.NotificationItem
import com.example.chillout.presentation.screen.main.notification.NotificationScreen

@Composable
fun NotificationItemRow(
    item: NotificationItem,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!item.isChecked) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.name, fontSize = 16.sp)
        Checkbox(
            checked = item.isChecked,
            onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF9370DB),
                uncheckedColor = Color.Gray
            )
        )
    }
}

