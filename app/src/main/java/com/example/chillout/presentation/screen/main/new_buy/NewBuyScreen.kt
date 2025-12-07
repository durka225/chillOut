package com.example.chillout.presentation.screen.main.new_buy

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chillout.App
import com.example.chillout.R
import com.example.chillout.presentation.navigation.Screen
import com.example.chillout.presentation.screen.main.new_buy.navigation.ScreenTo
import com.example.chillout.presentation.screen.viewmodel.NewBuyScreenViewModel
import com.example.chillout.presentation.ui.component.NewCategoryDialog
import com.example.chillout.presentation.ui.component.StyledButton
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

/*val mutableCategoriesState = mutableStateOf(
    listOf(
        "Транспортные средства",
        "Электроника",
        "Мебель",
        "Бытовая техника"
    )
)

fun addCategory(newCategory: String) {
    if (newCategory.isNotBlank() && newCategory !in mutableCategoriesState.value) {
        mutableCategoriesState.value = mutableCategoriesState.value + newCategory
    }
}*/

@Composable
fun ErrorMessage(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 40.dp, top = 4.dp).fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewBuyScreen(
    onNavigateTo: (ScreenTo) -> Unit = {},
    viewModel: NewBuyScreenViewModel = viewModel()
) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    if (username.isEmpty()){
        username = App.appContext().getSharedPreferences("local_storage", Context.MODE_PRIVATE).getString("username",null)!!
        Log.d("UserProfileSetupScreen","$username")
    }
    var isExpanded by remember { mutableStateOf(false) }
    var showNewCategoryDialog by remember { mutableStateOf(false) }

    val currentCategories by viewModel.categories.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box() {
                Text(
                    modifier = Modifier.padding(top = 20.dp, start = 30.dp),
                    text = "Добавление\nпокупки",
                    fontSize = 40.sp,
                    lineHeight = 40.sp
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp, start = 30.dp, end = 30.dp),
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
                isError = viewModel.isNameError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (viewModel.isNameError) MaterialTheme.colorScheme.error else Color.Transparent,
                    unfocusedBorderColor = if (viewModel.isNameError) MaterialTheme.colorScheme.error else Color.Transparent,
                    errorBorderColor = MaterialTheme.colorScheme.error
                ),
                placeholder = {
                    Text(text = stringResource(id = R.string.name_buy))
                }
            )
        }
        if (viewModel.isNameError) ErrorMessage(text = "Введите название покупки")

/*Card(
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
    value = viewModel.link,
    onValueChange = viewModel::updateLink,
    shape = RoundedCornerShape(12.dp),
    isError = viewModel.isLinkError,
    colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = if (viewModel.isLinkError) MaterialTheme.colorScheme.error else Color.Transparent,
        unfocusedBorderColor = if (viewModel.isLinkError) MaterialTheme.colorScheme.error else Color.Transparent,
        errorBorderColor = MaterialTheme.colorScheme.error
    ),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
    placeholder = {
        Text(text = stringResource(id = R.string.link))
    }
)
}
if (viewModel.isLinkError) ErrorMessage(text = "Введите ссылку на товар")*/
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
                value = viewModel.price,
                onValueChange = viewModel::updatePrice,
                shape = RoundedCornerShape(12.dp),
                isError = viewModel.isPriceError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (viewModel.isPriceError) MaterialTheme.colorScheme.error else Color.Transparent,
                    unfocusedBorderColor = if (viewModel.isPriceError) MaterialTheme.colorScheme.error else Color.Transparent,
                    errorBorderColor = MaterialTheme.colorScheme.error
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = {
                    Text(text = stringResource(id = R.string.price))
                }
            )
        }
        if (viewModel.isPriceError) ErrorMessage(text = "Введите корректную цену (число > 0)")

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, start = 30.dp, end = 30.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = {
                    isExpanded = !isExpanded
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .background(Color.White),
                    readOnly = true,
                    value = viewModel.categoryName,
                    onValueChange = { },
                    shape = RoundedCornerShape(12.dp),
                    isError = viewModel.isCategoryError,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (viewModel.isCategoryError) MaterialTheme.colorScheme.error else Color.Transparent,
                        unfocusedBorderColor = if (viewModel.isCategoryError) MaterialTheme.colorScheme.error else Color.Transparent,
                        errorBorderColor = MaterialTheme.colorScheme.error
                    ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = isExpanded
                        )
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.category))
                    }
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                ) {
                    currentCategories.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                viewModel.updateCategoryName(selectionOption)
                                isExpanded = false
                            },
                        )
                    }
                }
            }
        }
        if (viewModel.isCategoryError) ErrorMessage(text = "Выберите категорию")
        Text(
            text = stringResource(id = R.string.no_category),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable {
                    showNewCategoryDialog = true
                }
        )

        StyledButton(
            onClick = {
                if (viewModel.validateInputs()) {
                    newPurchase(
                        username = username,
                        name = viewModel.name,
                        price = viewModel.price.toInt(),
                        dataLock = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            .format(Date()),
                        categoryName = viewModel.categoryName,
                        status = "COOLING",
                        onResult = { success ->
                            Log.d("NewBuyScreen", "newPurchase result: $success")
                            if (success) {
                                Toast.makeText(context, "Покупка добавлена", Toast.LENGTH_SHORT).show()
                                Log.d("NewBuyScreen", "Navigating to Calculation")
                                onNavigateTo(
                                    ScreenTo.Calculation(
                                        purchaseName = viewModel.name,
                                        price = viewModel.price.toInt(),
                                        categoryName = viewModel.categoryName
                                    )
                                )

                            } else {
                                Log.e("NewBuyScreen", "Failed to add purchase")
                                Toast.makeText(context, "Ошибка добавления покупки", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                } else {
                    Toast.makeText(context, "Пожалуйста, заполните все поля корректно", Toast.LENGTH_SHORT).show()
                }
            },
            containerColor = Color(0xFFFFFF11),
            contentColor = Color.Black,
            modifier = Modifier.padding(top = 60.dp),
        ) {
            Text(
                text = stringResource(id = R.string.new_button),
                fontSize = 19.sp
            )
        }
    }

    if (showNewCategoryDialog) {
        NewCategoryDialog(
            onDismiss = { showNewCategoryDialog = false },
            onSave = {
                showNewCategoryDialog = false
                viewModel.addCategory(it, username)
                /*newCategoryName ->
                viewModel.addCategory(newCategoryName)
                viewModel.updateCategoryName(newCategoryName)
                showNewCategoryDialog = false*/
            }
        )
    }
}

@Composable
@Preview (showBackground = true)
fun NewBuyScreenPreview(){
    NewBuyScreen ()
}