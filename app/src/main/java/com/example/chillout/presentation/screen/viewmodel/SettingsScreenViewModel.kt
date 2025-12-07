package com.example.chillout.presentation.screen.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chillout.App
import com.example.chillout.api.dto.AuthRequest
import com.example.chillout.api.dto.newCoolingPeriod
import com.example.chillout.api.network.NetworkClient
import com.example.chillout.api.service.UserService
import com.example.chillout.presentation.screen.main.settings.CoolingRange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.toString

class SettingsScreenViewModel : ViewModel() {

    val retrofit = NetworkClient().retrofit

    private val userService = retrofit.create(UserService::class.java)

    // username берём так же, как на HomeScreen
    private val username: String by lazy {
        App.appContext()
            .getSharedPreferences("local_storage", Context.MODE_PRIVATE)
            .getString("username", "") ?: ""
    }

    var includeCurrentMoney by mutableStateOf(true)
        private set

    var coolingRanges: List<CoolingRange> by mutableStateOf(emptyList())
        private set

    var selectedCategory: String by mutableStateOf("Категория")
        private set

    var pollCount: String by mutableStateOf("7")
        private set

    var pollPeriod: String by mutableStateOf("дней")
        private set

    var selectedChannel: String by mutableStateOf("Telegram")
        private set

    init {
        loadCoolingRanges()
    }

    fun toggleIncludeCurrentMoney() {
        includeCurrentMoney = !includeCurrentMoney
    }

    /** Загрузка периодов с бэка */
    private fun loadCoolingRanges() {
        if (username.isEmpty()) return

        viewModelScope.launch {
            val result: List<newCoolingPeriod>? = withContext(Dispatchers.IO) {
                try {
                    val response = userService.getCoolingPeriod(username).execute()
                    if (response.isSuccessful) response.body() else null
                } catch (_: Exception) {
                    null
                }
            }

            result?.let { list ->
                val mapped: List<CoolingRange> = list.map { dto ->
                    dto.toUiModel()
                }
                // minPrice -> minAmount
                coolingRanges = mapped.sortedBy { range -> range.minAmount }
            }
        }
    }

    /** Добавление периода через бэк */
    fun addCoolingRange(range: CoolingRange) {
        if (username.isEmpty()) return

        viewModelScope.launch {
            val dto: newCoolingPeriod = range.toDto()

            val retrofit = NetworkClient().retrofit
            val userService = retrofit.create(UserService::class.java)

            val loginUser : Call<String> = userService.newCoolingPeriod(username, newCoolingPeriod(
                minPrice = dto.minPrice,
                maxPrice = dto.maxPrice,
                durationDays = dto.durationDays
            ))
            Log.d("LoginScreen", "Username for login: $username")

            loginUser.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        Log.d("LoginScreen", "Ответ сервера : ${response.body()}")
                        loadCoolingRanges()
                    } else {
                        Log.w("LoginScreen", "Код ошибки: ${response.code()}")
                        Log.w("LoginScreen", "Тело ошибки: ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    Log.e("AuthRequest", "Ошибка запроса: ${t.message}")
                }
            })
        }
    }

    /** Удаление периода через бэк */
    fun deleteCoolingRange(rangeId: String) {
        if (username.isEmpty()) return

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    Log.d("CoolingDelete", "Удаление id=$rangeId, username: $username")

                    val call = userService.deleteCoolingPeriod(username, rangeId)
                    val response = call.execute()

                    if (response.isSuccessful) {
                        Log.d("CoolingDelete", "Успешно удалено, код: ${response.code()}")
                        withContext(Dispatchers.Main) {
                            coolingRanges = coolingRanges.filter { range -> range.id != rangeId }
                        }
                    } else {
                        Log.w("CoolingDelete", "Код ошибки: ${response.code()}")
                        Log.w("CoolingDelete", "URL: ${call.request().url}")
                        Log.w("CoolingDelete", "Метод: ${call.request().method}")
                        Log.w(
                            "CoolingDelete",
                            "Тело ошибки: ${response.errorBody()?.string()}"
                        )
                    }
                } catch (t: Throwable) {
                    Log.e("CoolingDelete", "Сетевая ошибка: ${t.message}", t)
                }
            }
        }
    }


    fun updateSelectedCategory(category: String) {
        this.selectedCategory = category
    }

    fun updatePollCount(count: String) {
        this.pollCount = count.filter { it.isDigit() }
    }

    fun updatePollPeriod(period: String) {
        this.pollPeriod = period
    }

    fun updateSelectedChannel(channel: String) {
        this.selectedChannel = channel
    }

    fun saveSettings() {
        println("Settings saved: $coolingRanges, $pollCount, $selectedChannel")
    }

    /** Маппинг DTO -> UI-модель */
    private fun newCoolingPeriod.toUiModel(): CoolingRange =
        CoolingRange(
            id = this.id ?: "", // UUID из бэка
            minAmount = minPrice,
            maxAmount = maxPrice,
            durationCount = durationDays.toString(),
            durationUnit = "дней"
        )


    /** Маппинг UI-модель -> DTO */
    private fun CoolingRange.toDto(): newCoolingPeriod =
        newCoolingPeriod(
            minPrice = minAmount,
            maxPrice = maxAmount ?: 0,
            durationDays = durationCount.toIntOrNull() ?: 0
        )
}
