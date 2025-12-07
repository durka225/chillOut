package com.cybergarden.chillout.model;

import com.cybergarden.chillout.dto.Status;
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

    private LocalDateTime notificationTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Purchases() {
        this.name = "";
        this.cost = 0;
        this.dataLock = LocalDate.now();
        this.notificationTime = LocalDateTime.now();
        this.category = new Category();
        this.user = new User();
        this.status = Status.COOLING;
    }

    public Purchases(
            String name,
            Integer cost,
            LocalDate dataLock,
            LocalDateTime notificationTime,
            Category category,
            User user,
            Status status
    ) {
        this.name = name;
        this.cost = cost;
        this.dataLock = dataLock;
        this.notificationTime = notificationTime;
        this.category = category;
        this.user = user;
        this.status = status;
    }
}
