package com.cybergarden.chillout.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "categories")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    @Column(name = "is_default")
    private Boolean isDefault;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Purchases> purchases = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Category() {
        this.name = "";
        this.purchases = new ArrayList<>();
    }

    public Category(String name, User user) {
        this.name = name;
        this.purchases = new ArrayList<>();
        this.isDefault = false;
        this.user = user;
    }

    public Category(String name, Boolean isDefault) {
        this.name = name;
        this.purchases = new ArrayList<>();
        this.isDefault = isDefault;
    }
}
