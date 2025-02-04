package com.iticbcn.quimpelacals;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Empleat", uniqueConstraints = {
        @UniqueConstraint(columnNames = "DNI")
})
public class Empleat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToMany
    @JoinTable(
            name = "Empleat_Equip",
            joinColumns = @JoinColumn(name = "empleat_id"),
            inverseJoinColumns = @JoinColumn(name = "equip_id")
    )
    private List<Equip> equips;

    // Getters i Setters
}

enum Departament {
    SALES, MARKETING
}
