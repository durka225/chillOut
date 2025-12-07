package com.example.chillout.presentation.screen.main.home

import android.util.Log
import com.example.chillout.api.dto.ProfileResponse
import com.example.chillout.api.network.NetworkClient
import com.example.chillout.api.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getProfile(username: String, onResult: (ProfileResponse?) -> Unit) {
    val retrofit = NetworkClient().retrofit
    val userService = retrofit.create(UserService::class.java)

    val profileUser: Call<ProfileResponse> = userService.profileResponce(username = username)

    profileUser.enqueue(object : Callback<ProfileResponse> {
        override fun onResponse(
            call: Call<ProfileResponse?>,
            response: Response<ProfileResponse?>
        ) {
            if (response.isSuccessful && response.body() != null) {
                onResult(response.body())
            } else {
                onResult(null)
            }
        }

        override fun onFailure(
            call: Call<ProfileResponse?>,
            t: Throwable
        ) {
            Log.e("GetProfile", "Ошибка запроса: ${t.message}")
            onResult(null)
        }
    })
}
