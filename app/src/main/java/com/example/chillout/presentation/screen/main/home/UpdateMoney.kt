package com.example.chillout.presentation.screen.main.home

import com.example.chillout.api.dto.EditCurrentMoney
import com.example.chillout.api.network.NetworkClient
import com.example.chillout.api.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun updateMoney(newMoney: Int, username: String) {
    val retrofit = NetworkClient().retrofit
    val userService = retrofit.create(UserService::class.java)

    val updateMoney = userService.updateUserMoney(username = username, EditCurrentMoney(currentMoney = newMoney))

    updateMoney.enqueue( object : Callback<String> {
        override fun onResponse(call: Call<String?>, response: Response<String?>) {

        }

        override fun onFailure(call: Call<String?>, t: Throwable) {

        }
    })
}