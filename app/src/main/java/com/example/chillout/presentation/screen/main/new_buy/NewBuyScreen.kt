package com.example.chillout.presentation.screen.main.new_buy

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chillout.R
import com.example.chillout.presentation.navigation.Screen
import com.example.chillout.presentation.screen.viewmodel.NewBuyScreenViewModel
import com.example.chillout.presentation.ui.component.NewCategoryDialog
import com.example.chillout.presentation.ui.component.StyledButton

val mutableCategoriesState = mutableStateOf(
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewBuyScreen(
    onNavigateTo: (Screen) -> Unit = {},
    viewModel: NewBuyScreenViewModel = viewModel()
) {
    var isExpanded by remember { mutableStateOf(false) }
    var showNewCategoryDialog by remember { mutableStateOf(false) }
    val currentCategories = mutableCategoriesState.value

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
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                placeholder = {
                    Text(text = stringResource(id = R.string.name_buy))
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
                value = viewModel.price,
                onValueChange = viewModel::updatePrice,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                placeholder = {
                    Text(text = stringResource(id = R.string.price))
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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
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
                    onDismissRequest = {
                        isExpanded = false
                    }
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
            onClick = {onNavigateTo (Screen.Main)},
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
            onSave = { newCategoryName ->
                addCategory(newCategoryName)
                viewModel.updateCategoryName(newCategoryName)
                showNewCategoryDialog = false
            }
        )
    }
}



@Composable
@Preview (showBackground = true)
fun NewBuyScreenPreview(){
    NewBuyScreen ()
}