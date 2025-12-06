package com.example.chillout.presentation.screen.main.home

import android.util.Log
import com.example.chillout.api.dto.AuthRequest
import com.example.chillout.api.dto.ProfileResponce
import com.example.chillout.api.network.NetworkClient
import com.example.chillout.api.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun getProfile(username: String, onResult: (ProfileResponce) -> Unit) {
    val retrofit = NetworkClient().retrofit
    val userService = retrofit.create(UserService::class.java)

    val profileUser : Call<ProfileResponce> = userService.profileResponce(username = username)

    profileUser.enqueue(object : Callback<ProfileResponce> {
        override fun onResponse(
            call: Call<ProfileResponce?>,
            response: Response<ProfileResponce?>
        ) {
            if (response.isSuccessful) {
                onResult(ProfileResponce(
                    response.body()!!.username,
                    response.body()!!.name,
                    response.body()!!.wages,
                    response.body()!!.savingMoney,
                    response.body()!!.currentMoney,
                    response.body()!!.purchases
                ))
            }
        }

        override fun onFailure(
            call: Call<ProfileResponce?>,
            t: Throwable
        ) {
            TODO("Not yet implemented")
        }
    })
}