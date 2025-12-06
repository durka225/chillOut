package com.example.chillout.api.service

import com.example.chillout.api.dto.AuthRequest
import com.example.chillout.api.dto.AuthUserProfileSetupRequest
import com.example.chillout.api.dto.ProfileResponce
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("step1")
    fun authRequest(@Body request: AuthRequest) : Call<String>

    @POST("step2")
    fun authUserProfileSetup(@Body request: AuthUserProfileSetupRequest,
                        @Header("username") username: String) : Call<String>

    @GET("profile")
    fun profileResponce (@Header("username") username: String) : Call<ProfileResponce>
}