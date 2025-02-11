package com.iticbcn.quimpelacals.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Empleat", uniqueConstraints = {
        @UniqueConstraint(columnNames = "DNI")
})
public class Empleat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empleat_id;

    @Column(name = "DNI", nullable = false)
    private String dni;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "cognoms", nullable = false)
    private String cognoms;

    @Column(name = "tel", nullable = true)
    private String tel;

    @Column(name = "sou", nullable = false)
    private Double sou;

    @Enumerated(EnumType.STRING)
    @Column(name = "departament", nullable = false)
    private Departament departament;

    @OneToMany(mappedBy = "empleat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tasca> tasques;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Empleat_Equip",
            joinColumns = @JoinColumn(name = "empleat_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Equip> equips;

    // Getters i Setters

    public Empleat() {}

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }
    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }

    public Double getSou() {
        return sou;
    }
    public void setSou(Double sou) {
        this.sou = sou;
    }

    public Departament getDepartament() {
        return departament;
    }
    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    public List<Tasca> getTasques() {
        return tasques;
    }
    public void setTasques(List<Tasca> tasques) {
        this.tasques = tasques;
    }

    public List<Equip> getEquips() {
        return equips;
    }
    public void setEquips(List<Equip> equips) {
        this.equips = equips;
    }

    @Override
    public String toString() {
        return "ID: " + getEmpleat_id() +
                "\n DNI: " + getDni() +
                "\n Nom complet: " + getNom() + " " + getCognoms() +
                "\n Telèfon: " + (getTel() != null ? getTel() : "No especificat") +
                "\n Sou: " + getSou() + "€" +
                "\n Departament: " + getDepartament() + "\n";
    }

    public Long getEmpleat_id() {
        return empleat_id;
    }

    public void setEmpleat_id(Long empleat_id) {
        this.empleat_id = empleat_id;
    }
}

