package com.iticbcn.quimpelacals.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Tasca", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nom")
})
public class Tasca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "descripcio", nullable = true)
    private String descripcio;

    @Column(name = "prioritat", nullable = false)
    private String prioritat;

    @Column(name = "data_lim", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataLim;

    @OneToMany(mappedBy = "tasca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Historic> historics = new ArrayList<>();

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

    public String getDescripcio() {
        return descripcio;
    }
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getPrioritat() {
        return prioritat;
    }
    public void setPrioritat(String prioritat) {
        this.prioritat = prioritat;
    }

    public Date getDataLim() {
        return dataLim;
    }
    public void setDataLim(Date dataLim) {
        this.dataLim = dataLim;
    }

    public List<Historic> getHistorics() {
        return historics;
    }
    public void setHistorics(List<Historic> historics) {
        this.historics = historics;
    }
}