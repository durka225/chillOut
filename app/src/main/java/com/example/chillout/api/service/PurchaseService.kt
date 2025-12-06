package com.example.chillout.api.service

import com.example.chillout.api.dto.AuthRequest
import com.example.chillout.api.dto.PurchaseRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PurchaseService {
    @POST("purchase/new")
    fun purchaseRequest(@Body request: PurchaseRequest,
                        @Header("username") username: String) : Call<String>
}