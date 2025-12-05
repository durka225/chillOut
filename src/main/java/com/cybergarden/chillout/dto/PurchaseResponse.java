package com.cybergarden.chillout.dto;

import com.cybergarden.chillout.model.Purchases;

import java.time.LocalDate;
import java.util.List;

public record PurchaseResponse (
        String name,
        Integer price,
        String categoryName,
        LocalDate dataLock
) { }
