package com.iticbcn.quimpelacals;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Historic")
public class Historic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_inici", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataInici;

    @Column(name = "data_fi", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dataFi;

    @Column(name = "comentaris", nullable = true)
    private String comentaris;

    @ManyToOne
    @JoinColumn(name = "tasca_id", nullable = false)
    private Tasca tasca;

    // Getters i Setters
}
