package com.cybergarden.chillout.dto;

import com.cybergarden.chillout.model.Purchases;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.List;

public record PurchaseResponse (
        String uuid,
        String name,
        Integer price,
        String categoryName,
        LocalDate dataLock,
        @SerializedName("status")
        Status status
) { }
