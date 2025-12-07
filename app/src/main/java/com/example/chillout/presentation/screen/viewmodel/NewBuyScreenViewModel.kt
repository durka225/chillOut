package com.example.chillout.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

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

        if (link.isBlank()) {
            isLinkError = true
            isValid = false
        }

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
}