package com.example.chillout.presentation.screen.main.home

import com.example.chillout.api.network.NetworkClient
import com.example.chillout.api.service.PurchaseService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun buyPurchase(uuid : String, username: String) {
    val retrofit = NetworkClient().retrofit

    val purchaseService = retrofit.create(PurchaseService::class.java)

    purchaseService.buyPurchase(uuid = uuid, username = username).enqueue( object : Callback<String>{
        override fun onResponse(
            call: Call<String>,
            response: Response<String>
        ) {
            if (response.isSuccessful) {

            }
        }

        override fun onFailure(call: Call<String?>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })
}