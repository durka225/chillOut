package com.cybergarden.chillout.dto;

public record ProfileUserResponse(
        String username,
        String name,
        Integer wages,
        Integer savingMoney,
        Integer currentMoney
) { }
