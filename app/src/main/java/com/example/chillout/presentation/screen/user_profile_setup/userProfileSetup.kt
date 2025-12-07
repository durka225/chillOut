package com.example.chillout.presentation.screen.user_profile_setup

import android.util.Log
import com.example.chillout.api.dto.AuthUserProfileSetupRequest
import com.example.chillout.api.network.NetworkClient
import com.example.chillout.api.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun userProfileSetup(
    username: String,
    name: String,
    wages: Int,
    savingMoney: Int,
    currentMoney: Int,
    onResult : (Boolean) -> Unit) {
    val retrofit = NetworkClient().retrofit
    val userService = retrofit.create(UserService::class.java)

    val regUserComplete : Call<String> = userService.authUserProfileSetup(AuthUserProfileSetupRequest(name, wages, 0, currentMoney, savingMoney),username)

    regUserComplete.enqueue(object : Callback<String> {
        override fun onResponse(
            call: Call<String>,
            response: Response<String>
        ) {
            if (response.isSuccessful) {
                Log.d("Response on server", "Ответ сервера : ${response.body()}")
                onResult(response.isSuccessful)
            } else {
                Log.w("Response on server", "Код ошибки: ${response.code()}")
                Log.w("Response on server", "Тело ошибки: ${response.message()}")
                onResult(response.isSuccessful)
            }
        }

        override fun onFailure(call: Call<String?>, t: Throwable) {
            Log.e("RegisterScreenState", "Ошибка запроса: ${t.message}")
            onResult(false)
        }
    })
}