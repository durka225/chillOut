package com.example.chillout.api.service

import com.example.chillout.api.dto.AuthRequest
import com.example.chillout.api.dto.AuthUserProfileSetupRequest
import com.example.chillout.api.dto.EditCurrentMoney
import com.example.chillout.api.dto.ProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {
    @POST("registration/credentials")
    fun authRequest(@Body request: AuthRequest) : Call<String>

    @POST("registration/personal-info")
    fun authUserProfileSetup(@Body request: AuthUserProfileSetupRequest,
                        @Header("username") username: String) : Call<String>

    @GET("profile")
    fun profileResponce (@Header("username") username: String) : Call<ProfileResponse>

    @PUT("api/user/money")
    fun updateUserMoney(
        @Header("username") username: String,
        @Body request: EditCurrentMoney
    ) : Call<String>
}