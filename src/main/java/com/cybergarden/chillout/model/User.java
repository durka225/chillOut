package com.cybergarden.chillout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(unique = true)
    private String username;

    private String firebaseToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchases> purchases = new ArrayList<>();

    public User(String username, String firebaseToken) {
        this.username = username;
        this.firebaseToken = firebaseToken;
        this.purchases = new ArrayList<>();
    }

    public User(String username){
        this.username = username;
        this.purchases = new ArrayList<>();
    }

    public User() {
        this.username = "";
        this.purchases = new ArrayList<>();
    }
}
