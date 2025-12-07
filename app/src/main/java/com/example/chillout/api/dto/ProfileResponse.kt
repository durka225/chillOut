package com.example.chillout.api.dto

import com.example.chillout.presentation.screen.main.home.Purchase

data class ProfileResponse(
    var username: String,
    val name: String,
    var wages: Int,
    var savingMoney:Int,
    var currentMoney: Int,
    var postpone: Int,
    var purchases: List<Purchase>
)