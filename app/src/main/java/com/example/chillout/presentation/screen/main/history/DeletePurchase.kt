package com.example.chillout.presentation.screen.main.history

import com.example.chillout.api.network.NetworkClient
import com.example.chillout.api.service.PurchaseService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun deletePurchase (uuid: String, username: String) {

    val retrofit = NetworkClient().retrofit
    val purchaseService = retrofit.create(PurchaseService::class.java)

    val deletePurchase : Call<String> = purchaseService.deletePurchase(username = username, uuid = uuid)

    deletePurchase.enqueue( object : Callback<String> {
        override fun onResponse(
            call: Call<String?>,
            response: Response<String?>
        ) {
            if (response.isSuccessful) {
                // Purchase deleted successfully
            }
        }

        override fun onFailure(
            call: Call<String?>,
            t: Throwable
        ) {
            // Handle failure
        }
    })
}