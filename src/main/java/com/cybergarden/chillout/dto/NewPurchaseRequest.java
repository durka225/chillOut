package com.cybergarden.chillout.dto;

import java.time.LocalDate;

public record NewPurchaseRequest(
        String name,
        Integer price,
        LocalDate dataLock,
        LocalDateTime notificationTime,
        String categoryName,
        String status
) {
}
