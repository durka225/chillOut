package com.cybergarden.chillout.dto;

import com.cybergarden.chillout.model.Purchases;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ProfileUserResponse(
        String username,
        String name,
        Integer wages,
        Integer savingMoney,
        Integer currentMoney,
        List<Purchases> purchases
) { }
