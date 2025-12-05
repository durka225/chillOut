package com.cybergarden.chillout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

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

    public User(String username){
        this.username = username;
    }

    public User() {
        this.username = "";
    }
}
