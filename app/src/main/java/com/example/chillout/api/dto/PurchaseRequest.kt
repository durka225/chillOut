package com.example.chillout.api.dto

data class PurchaseRequest(
    var name: String,
    var price: Int,
    var dataLock: String,
    var categoryName: String,
    var status: String
)
