package com.example.chillout.api.dto

data class AuthUserProfileSetupRequest(
    var name: String = "",
    var wages: Int,
    var savingMoney: Int,
    var currentMoney: Int,
    var postpone: Int
)
