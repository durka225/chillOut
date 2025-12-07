package com.example.chillout.presentation.screen.main.new_buy

import com.example.chillout.api.network.NetworkClient
import com.example.chillout.api.service.CategoryService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun newCategory(name: String, username: String) {
    val retrofit = NetworkClient().retrofit
    val categoryService = retrofit.create(CategoryService::class.java)

    val newCategory = categoryService.newCategory(categoryName = name, username = username)

    newCategory.enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String?>, response: Response<String?>) {
        }
        override fun onFailure(call: Call<String?>, t: Throwable) {
        }
    })
}