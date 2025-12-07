package com.example.chillout.api.dto

data class newCoolingPeriod (
    val id: String? = null, // UUID от сервера (при создании может быть null)
    val minPrice: Int,
    val maxPrice: Int,
    val durationDays: Int
)