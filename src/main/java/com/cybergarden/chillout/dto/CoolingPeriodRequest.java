package com.cybergarden.chillout.dto;

public record CoolingPeriodRequest(
        Integer minPrice,
        Integer maxPrice,
        Integer durationDays
) {}