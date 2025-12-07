package com.cybergarden.chillout.dto;

import java.util.UUID;

public record CoolingPeriodResponse(
        UUID id,
        Integer minPrice,
        Integer maxPrice,
        Integer durationDays
) {}