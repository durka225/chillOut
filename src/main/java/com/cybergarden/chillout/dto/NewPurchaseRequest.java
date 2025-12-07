package com.cybergarden.chillout.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record NewPurchaseRequest(
        String name,
        Integer price,
        LocalDate dataLock,
        LocalDateTime notificationTime,
        String categoryName,
        String status
) {
}
