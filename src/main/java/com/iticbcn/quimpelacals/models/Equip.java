package com.iticbcn.quimpelacals.models;

import java.io.Serializable;
import java.util.Set;

public class Equip implements Serializable{

    private Long id;
    private String nom;
    private Set<Empleat> empleats;

    public Equip() {}

    // Getters i Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Empleat> getEmpleats() {
        return empleats;
    }
    public void setEmpleats(Set<Empleat> empleats) {
        this.empleats = empleats;
    }

}