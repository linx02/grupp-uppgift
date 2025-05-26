package com.caw24g.grupp_uppgift.models;

import jakarta.persistence.*;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    public City(String name) {
        this.name = name;
    }

    public City() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
