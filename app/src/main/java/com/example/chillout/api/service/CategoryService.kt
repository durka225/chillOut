package com.example.chillout.api.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CategoryService {

    @GET("category")
    fun getCategory(@Header("username") username: String) : Call<List<String>>

    @POST("category/new")
    fun newCategory(@Header("username") username: String,
                    @Query("categoryName") categoryName: String ) : Call<String>
}