package com.example.chillout.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chillout.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chillout.presentation.navigation.Screen
import com.example.chillout.presentation.screen.viewmodel.LoginScreenViewModel
import com.example.chillout.presentation.ui.component.StyledButton

@Composable
fun LoginScreen(
    onNavigateTo: (Screen) -> Unit = {},
    viewModel: LoginScreenViewModel = viewModel()
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFF11)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.padding(top = 120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logobig),
                contentDescription = "login image",
                modifier = Modifier
                    .size(240.dp)
            )
        }
        Spacer(modifier = Modifier.height(50.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                value = viewModel.username,
                onValueChange = viewModel::updateUsername,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Outlined.AccountCircle),
                        contentDescription = null
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.username)
                    )
                }
            )
        }

        StyledButton(
            onClick = { onNavigateTo (Screen.UserProfileSetup)},
            modifier = Modifier.padding(top = 50.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login_button),
                fontSize = 19.sp
            )
        }

    }
}
@Composable
@Preview (showBackground = true)
fun LoginScreenPreview(){
    LoginScreen()
}