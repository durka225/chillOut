package com.cybergarden.chillout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "user_details")
@Getter
@Setter
public class UserDetails {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String name;
    private Integer wages;
    private Integer savingMoney;
    private Integer currentMoney;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public UserDetails() {
        this.name = "";
        this.wages = 0;
        this.savingMoney = 0;
        this.currentMoney = 0;
        this.user = new User();
    }

    public UserDetails(String name, Integer integer, Integer wages, Integer integer1, User user) {
        this.name = name;
        this.wages = wages;
        this.savingMoney = integer;
        this.currentMoney = integer1;
        this.user = user;
    }
}
