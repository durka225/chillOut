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

    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Purchases> purchases = new ArrayList<>();

    public Category() {
        this.name = "";
        this.purchases = new ArrayList<>();
    }

    public Category(String name) {
        this.name = name;
        this.purchases = new ArrayList<>();
    }
}
