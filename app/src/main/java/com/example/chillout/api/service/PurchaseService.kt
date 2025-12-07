package com.example.chillout.api.service

import com.example.chillout.api.dto.AuthRequest
import com.example.chillout.api.dto.PurchaseRequest
import com.example.chillout.presentation.screen.main.home.Purchase
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PurchaseService {
    @POST("purchase/new")
    fun purchaseRequest(@Body request: PurchaseRequest,
                        @Header("username") username: String) : Call<String>

    @GET("purchase")
    fun getHistoryPurchase(
        @Header("username") username: String
    ) : Call<List<Purchase>>

    @DELETE("purchase/delete")
    fun deletePurchase(
        @Header("username") username: String,
        @Query("uuid") uuid: String
    ) : Call<String>

    @PUT("purchase/cancel")
    fun cancelPurchase(
        @Header("username") username: String,
        @Query("uuid") uuid: String
    ) : Call<String>

    @PUT("purchase/buy")
    fun buyPurchase(
        @Header("username") username: String,
        @Query("uuid") uuid: String
    ) : Call<String>
}