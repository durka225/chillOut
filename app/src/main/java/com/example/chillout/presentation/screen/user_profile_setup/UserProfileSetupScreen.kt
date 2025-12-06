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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
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
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
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

        InputCard(
            value = viewModel.name,
            onValueChange = viewModel::updateName,
            placeholderRes = R.string.name,
            isError = viewModel.isNameError,
            errorMessage = "Введите ваше имя"
        )

        InputCard(
            value = viewModel.wages,
            onValueChange = viewModel::updateWages,
            placeholderRes = R.string.wages,
            isError = viewModel.isWagesError,
            errorMessage = "Введите положительное число",
            keyboardType = KeyboardType.Number
        )

        InputCard(
            value = viewModel.savingMoney,
            onValueChange = viewModel::updateSavingMoney,
            placeholderRes = R.string.savingMoney,
            isError = viewModel.isSavingMoneyError,
            errorMessage = "Введите число (может быть 0)",
            keyboardType = KeyboardType.Number
        )

        InputCard(
            value = viewModel.currentMoney,
            onValueChange = viewModel::updateCurrentMoney,
            placeholderRes = R.string.currentMoney,
            isError = viewModel.isCurrentMoneyError,
            errorMessage = "Введите число (может быть 0)",
            keyboardType = KeyboardType.Number
        )

        StyledButton(
            onClick = {
                if (viewModel.validateInputs()) {
                    userProfileSetup(
                        username,
                        viewModel.name,
                        viewModel.wages.toInt(),
                        viewModel.savingMoney.toInt(),
                        viewModel.currentMoney.toInt()
                    ){ ok ->
                        if (ok) {
                            onNavigateTo(Screen.Main)
                        } else {
                            Toast.makeText(
                                context,
                                "Ошибка сервера. Что-то пошло не так",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Пожалуйста, исправьте ошибки в полях",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            containerColor = Color(0xFFFFFF11),
            contentColor = Color.Black,
            modifier = Modifier.padding(top = 60.dp, bottom = 40.dp),
        ) {
            Text(
                text = stringResource(id = R.string.save_button),
                fontSize = 19.sp
            )
        }
    }
}

@Composable
private fun InputCard(
    value: String,
    onValueChange: (String) -> Unit,
    placeholderRes: Int,
    isError: Boolean,
    errorMessage: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                value = value,
                onValueChange = onValueChange,
                shape = RoundedCornerShape(12.dp),

                isError = isError,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                    unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error else Color.Transparent,
                    errorBorderColor = MaterialTheme.colorScheme.error
                ),

                placeholder = {
                    Text(
                        text = stringResource(id = placeholderRes)
                    )
                }
            )
        }

        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp, top = 4.dp)
            )
        }
    }
}


@Composable
@Preview (showBackground = true)
fun UserProfileSetupScreenPreview(){
    UserProfileSetupScreen ()
}
