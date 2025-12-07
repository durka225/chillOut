package com.example.chillout.presentation.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class NewBuyScreenViewModel: ViewModel () {
    var name by mutableStateOf("")
        private set

    var link by mutableStateOf("")
        private set

    var price by mutableStateOf("")
        private set

    var categoryName by mutableStateOf("")
        private set


    fun updateName(name: String){
        this.name = name
    }
    fun updateLink(link: String){
        this.link = link
    }
    fun updatePrice (price: String){
        this.price = price
    }
    fun updateCategoryName(categoryName: String){
        this.categoryName = categoryName
    }
}