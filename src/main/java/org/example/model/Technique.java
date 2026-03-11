package org.example.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Technique {
    private String name;
    private String type;
    private Sorcerer owner;
    private int damage;

    public Technique() {
    }

    public Technique(String name, String type, Sorcerer owner, int damage) {
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Sorcerer getOwner() {
        return owner;
    }

    public void setOwner(Sorcerer owner) {
        this.owner = owner;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @JsonSetter("owner")
    public void setOwnerFromString(String ownerName) {
        if (ownerName == null || ownerName.isBlank()) {
            this.owner = null;
        } else {
            Sorcerer sorcerer = new Sorcerer();
            sorcerer.setName(ownerName);
            this.owner = sorcerer;
        }
    }

    @JsonGetter("owner")
    public String getOwnerAsString() {
        return owner != null ? owner.getName() : null;
    }

    @Override
    public String toString() {
        return name + " " + type + " урон: " + damage +
                ", владелец: " + (owner != null ? owner.getName() : "неизвестен");
    }
}