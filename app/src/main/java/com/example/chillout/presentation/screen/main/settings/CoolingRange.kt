package com.example.chillout.presentation.screen.main.settings

data class CoolingRange(
    val id: String,
    val minAmount: Int,
    val maxAmount: Int? = null,
    val durationCount: String,
    val durationUnit: String
)
