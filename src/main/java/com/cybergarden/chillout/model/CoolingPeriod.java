package com.cybergarden.chillout.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "cooling_periods")
@Getter
@Setter
public class CoolingPeriod {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private Integer minPrice;
    private Integer maxPrice;
    private Integer durationDays;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public CoolingPeriod() {}

    public CoolingPeriod(Integer minPrice, Integer maxPrice, Integer durationDays, User user) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.durationDays = durationDays;
        this.user = user;
    }
}