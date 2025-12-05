package com.cybergarden.chillout.dto;

public record RegDetailsRequest(
        String name,
        Integer wages,
        Integer savingMoney,
        Integer currentMoney
) { }
