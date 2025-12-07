package com.example.chillout.presentation.screen.main.home

import kotlinx.serialization.SerialName
import java.time.LocalDate
import java.util.UUID

data class Purchase(
    val uuid: String,
    val name: String,
    val price: Int,
    val categoryName: String,
    val dataLock: String,
    @SerialName("status")
    val status: String
)
