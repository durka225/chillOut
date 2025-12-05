package com.example.chillout.api.service

import com.example.chillout.api.dto.AuthRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("v1/auth/")
    fun authUser(@Body request: AuthRequest) : Call<String>
}