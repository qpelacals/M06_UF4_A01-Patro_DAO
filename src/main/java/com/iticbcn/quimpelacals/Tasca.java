package com.iticbcn.quimpelacals;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;

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
}
