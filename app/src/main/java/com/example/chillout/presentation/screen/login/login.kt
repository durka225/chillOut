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

    loginUser.enqueue(object : Callback<String> {
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
            Log.e("AuthRequest", "Ошибка запроса: ${t.message}")
            onResult(false)
        }
    })
}