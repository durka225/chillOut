package com.cybergarden.chillout.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RegDetailsRequest")
public record RegDetailsRequest(
        String name,
        Integer wages,
        Integer savingMoney,
        Integer currentMoney
) { }
