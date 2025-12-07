package com.example.chillout.presentation.screen.main.new_buy

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chillout.App
import com.example.chillout.api.dto.ProfileResponse
import com.example.chillout.api.dto.newCoolingPeriod
import com.example.chillout.api.network.NetworkClient
import com.example.chillout.api.service.UserService
import com.example.chillout.presentation.screen.main.home.buyPurchase
import com.example.chillout.presentation.screen.main.home.cancelPurchase
import com.example.chillout.presentation.screen.main.home.getProfile
import com.example.chillout.presentation.screen.main.new_buy.navigation.ScreenTo
import com.example.chillout.presentation.screen.viewmodel.CalculationScreenViewModel
import com.example.chillout.presentation.ui.component.ActionButtonsRow
import com.example.chillout.presentation.ui.component.BuyStatusBlock
import com.example.chillout.presentation.ui.component.PurchasePredictionCard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.compareTo


@Composable
fun CalculationScreen(
    purchaseName: String,
    price: Int,
    categoryName: String,
    onNavigateTo: (ScreenTo) -> Unit = {},
    viewModel: CalculationScreenViewModel = viewModel()
) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var purchaseUuid by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        username = App.appContext()
            .getSharedPreferences("local_storage", Context.MODE_PRIVATE)
            .getString("username", "") ?: ""
        viewModel.setPurchaseData(purchaseName, price, categoryName)

        getPurchaseUuid(username, purchaseName) { uuid ->
            purchaseUuid = uuid ?: ""
        }

        getCoolingPeriodForCategory(username, price) { coolingDays ->
            viewModel.setCoolingPeriod(coolingDays)
        }

        getProfile(username) { profile ->
            profile?.let {
                viewModel.calculateDaysToPurchase(
                    savingMoney = it.savingMoney,
                    currentMoney = it.currentMoney,
                    price = price
                )
            }
            isLoading = false
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Загрузка...")
        }
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 40.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Расчет покупки",
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            BuyStatusBlock(
                purchaseName = viewModel.purchaseName,
                onDeferClick = {
                    viewModel.toggleDeferred(true)
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "${viewModel.coolingPeriodDays} дней",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "срок охлаждения",
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            PurchasePredictionCard(days = viewModel.daysToPurchase)

            Spacer(modifier = Modifier.height(48.dp))

            ActionButtonsRow(
                onBuyNow = {
                    if (purchaseUuid.isNotEmpty()) {
                        buyPurchase(purchaseUuid, username)
                        Toast.makeText(context, "Покупка совершена!", Toast.LENGTH_SHORT).show()
                        onNavigateTo(ScreenTo.NewBuy)
                    } else {
                        Toast.makeText(context, "Ошибка: UUID покупки не найден", Toast.LENGTH_SHORT).show()
                    }
                },
                onCancel = {
                    if (purchaseUuid.isNotEmpty()) {
                        cancelPurchase(purchaseUuid, username)
                        Toast.makeText(context, "Покупка отменена", Toast.LENGTH_SHORT).show()
                        onNavigateTo(ScreenTo.NewBuy)
                    } else {
                        Toast.makeText(context, "Ошибка: UUID покупки не найден", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun getCoolingPeriodForCategory(
    username: String,
    price: Int,
    onResult: (Int) -> Unit
) {
    val retrofit = NetworkClient().retrofit
    val userService = retrofit.create(UserService::class.java)

    userService.getCoolingPeriod(username).enqueue(object : Callback<List<newCoolingPeriod>> {
        override fun onResponse(
            call: Call<List<newCoolingPeriod>>,
            response: Response<List<newCoolingPeriod>>
        ) {
            if (response.isSuccessful) {
                val period = response.body()?.find { it.minPrice <= price && it.maxPrice >= price }
                onResult(period?.durationDays ?: 0)
            } else {
                onResult(0)
            }
        }

        override fun onFailure(call: Call<List<newCoolingPeriod>>, t: Throwable) {
            onResult(0)
        }
    })
}

fun getPurchaseUuid(
    username: String,
    purchaseName: String,
    onResult: (String?) -> Unit
) {
    val retrofit = NetworkClient().retrofit
    val userService = retrofit.create(UserService::class.java)

    val getProfile = userService.profileResponce(username)

    getProfile.enqueue(object : Callback<ProfileResponse> {
        override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
            if (response.isSuccessful) {
                val purchase = response.body()?.purchases?.find { it.name == purchaseName }
                onResult(purchase?.uuid)
            } else {
                onResult(null)
            }
        }

        override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
            onResult(null)
        }
    })
}

/*@Preview(showBackground = true)
@Composable
fun CalculationScreenPreview() {
    CalculationScreen()
}*/