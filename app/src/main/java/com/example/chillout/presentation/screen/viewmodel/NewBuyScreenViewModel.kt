package com.example.chillout.presentation.screen.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chillout.App
import com.example.chillout.api.network.NetworkClient
import com.example.chillout.api.service.CategoryService
import com.example.chillout.presentation.screen.main.new_buy.newCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewBuyScreenViewModel : ViewModel() {

    var name by mutableStateOf("")
        private set
    var link by mutableStateOf("")
        private set
    var price by mutableStateOf("")
        private set
    var categoryName by mutableStateOf("")
        private set

    var isNameError by mutableStateOf(false)
        private set
    var isLinkError by mutableStateOf(false)
        private set
    var isPriceError by mutableStateOf(false)
        private set
    var isCategoryError by mutableStateOf(false)
        private set

    fun updateName(newValue: String) {
        name = newValue
        isNameError = false
    }

    fun updateLink(newValue: String) {
        link = newValue
        isLinkError = false
    }

    fun updatePrice(newValue: String) {

        price = newValue.filter { it.isDigit() }
        isPriceError = false
    }

    fun updateCategoryName(newValue: String) {
        categoryName = newValue
        isCategoryError = false
    }

    fun validateInputs(): Boolean {
        var isValid = true

        if (name.isBlank()) {
            isNameError = true
            isValid = false
        }

        /*if (link.isBlank()) {
            isLinkError = true
            isValid = false
        }*/

        val priceInt = price.toIntOrNull()
        if (priceInt == null || priceInt <= 0) {
            isPriceError = true
            isValid = false
        }

        if (categoryName.isBlank()) {
            isCategoryError = true
            isValid = false
        }

        return isValid
    }

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    init {
        // Получите username здесь, например: username = getUsernameFromPrefs()
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            val username = App.appContext().getSharedPreferences("local_storage", Context.MODE_PRIVATE)
                .getString("username", "") ?: ""
            val retrofit = NetworkClient().retrofit
            val categoryService = retrofit.create(CategoryService::class.java)

            categoryService.getCategory(username).enqueue(object : Callback<List<String>> {
                override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                    if (response.isSuccessful) {
                        _categories.value = (response.body() ?: emptyList()).sorted()
                    } else {
                        // Обработка ошибки, например, лог или toast
                    }
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    // Обработка ошибки
                }
            })
        }
    }

    fun addCategory(newCategory: String, username: String) {
        if (newCategory.isNotBlank() && newCategory !in _categories.value) {
            newCategory(newCategory, username)
            _categories.value = (_categories.value + newCategory).sorted()
        }
    }
}