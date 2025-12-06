package com.example.chillout.presentation.screen.user_profile_setup

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chillout.App
import com.example.chillout.R
import com.example.chillout.presentation.navigation.Screen
import com.example.chillout.presentation.screen.viewmodel.UserProfileSetupScreenViewModel
import com.example.chillout.presentation.ui.component.StyledButton

@Composable
fun UserProfileSetupScreen(
    onNavigateTo: (Screen) -> Unit = {},
    viewModel: UserProfileSetupScreenViewModel = viewModel()
){
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    if (username.isEmpty()){
        username = App.appContext().getSharedPreferences("local_storage", Context.MODE_PRIVATE).getString("username",null)!!
        Log.d("UserProfileSetupScreen","$username")
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
        modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
        ) {
            Text(
                modifier = Modifier.padding(top = 25.dp, start = 30.dp),
                text = "Анкета",
                fontSize = 40.sp
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp, start = 30.dp, end = 30.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                value = viewModel.name,
                onValueChange = viewModel::updateName,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),

                placeholder = {
                    Text(
                        text = stringResource(id = R.string.name)
                    )
                }
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 30.dp, end = 30.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                value = viewModel.wages,
                onValueChange = viewModel::updateWages,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.wages)
                    )
                }
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 30.dp, end = 30.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                value = viewModel.savingMoney,
                onValueChange = viewModel::updateSavingMoney,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.savingMoney)
                    )
                }
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 30.dp, end = 30.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                value = viewModel.currentMoney,
                onValueChange = viewModel::updateCurrentMoney,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.currentMoney)
                    )
                }
            )
        }
        StyledButton(
            onClick = {
                userProfileSetup(username, viewModel.name, viewModel.wages.toInt(), viewModel.savingMoney.toInt(), viewModel.currentMoney.toInt()){ ok ->
                    if (ok) {
                        onNavigateTo(Screen.Main)
                    } else {
                        Toast.makeText(
                            context,
                            "Что-то пошло не так",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            containerColor = Color(0xFFFFFF11),
            contentColor = Color.Black,
            modifier = Modifier.padding(top = 60.dp),

        ) {
            Text(
                text = stringResource(id = R.string.save_button),
                fontSize = 19.sp
            )
        }

    }
}
@Composable
@Preview (showBackground = true)
fun UserProfileSetupScreenPreview(){
    UserProfileSetupScreen ()
}
