package com.cybergarden.chillout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "purchases")
@Getter
@Setter
public class Purchases {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String name;

    private Integer cost;

    private LocalDate dataLock;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Purchases() {
        this.name = "";
        this.cost = 0;
        this.dataLock = LocalDate.now();
        this.category = new Category();
        this.user = new User();
    }

    public Purchases(
            String name,
            Integer cost,
            LocalDate dataLock,
            Category category,
            User user
    ) {
        this.name = name;
        this.cost = cost;
        this.dataLock = dataLock;
        this.category = category;
        this.user = user;
    }
}
