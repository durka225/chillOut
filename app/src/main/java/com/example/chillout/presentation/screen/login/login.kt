package com.example.chillout.presentation.screen.login

import android.util.Log
import com.example.chillout.api.dto.AuthRequest
import com.example.chillout.api.network.NetworkClient
import com.example.chillout.api.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun login(username : String, onResult : (Boolean) -> Unit) {
    val retrofit = NetworkClient().retrofit
    val userService = retrofit.create(UserService::class.java)

    val loginUser : Call<String> = userService.authRequest(AuthRequest(username))
    Log.d("LoginScreen", "Username for login: $username")

    loginUser.enqueue(object : Callback<String> {
        override fun onResponse(
            call: Call<String>,
            response: Response<String>
        ) {
            if (response.isSuccessful) {
                Log.d("LoginScreen", "Ответ сервера : ${response.body()}")
                onResult(false)
            } else {
                Log.w("LoginScreen", "Код ошибки: ${response.code()}")
                Log.w("LoginScreen", "Тело ошибки: ${response.body()}")
                onResult(true)
            }
        }

        override fun onFailure(call: Call<String?>, t: Throwable) {
            Log.e("AuthRequest", "Ошибка запроса: ${t.message}")
            onResult(false)
        }
    })
}