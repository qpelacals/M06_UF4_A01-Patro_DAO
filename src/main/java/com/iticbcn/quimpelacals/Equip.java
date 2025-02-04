package com.iticbcn.quimpelacals;

import java.util.List;

public class Equip {

    private Long id;
    private String nom;
    private List<Empleat> empleats;

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

    public List<Empleat> getEmpleats() {
        return empleats;
    }
    public void setEmpleats(List<Empleat> empleats) {
        this.empleats = empleats;
    }
}