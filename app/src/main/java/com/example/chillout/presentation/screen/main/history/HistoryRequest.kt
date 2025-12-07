package com.example.chillout.presentation.screen.main.history

import com.example.chillout.api.network.NetworkClient
import com.example.chillout.api.service.PurchaseService
import com.example.chillout.presentation.screen.main.home.Purchase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun historyRequest(username: String, onResult : (List<Purchase>) -> Unit ) {
    val retrofit = NetworkClient().retrofit

    val purchaseService = retrofit.create(PurchaseService::class.java)

    val purchaseHistory : Call<List<Purchase>> = purchaseService.getHistoryPurchase(username = username)

    purchaseHistory.enqueue( object : Callback<List<Purchase>> {
        override fun onResponse(call: Call<List<Purchase>?>, response: Response<List<Purchase>?>) {
            onResult(response.body() ?: emptyList())
        }

        override fun onFailure(call: Call<List<Purchase>?>, t: Throwable) {

        }
    })
}