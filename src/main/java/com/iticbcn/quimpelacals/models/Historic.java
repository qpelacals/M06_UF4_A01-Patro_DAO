package com.iticbcn.quimpelacals.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class Historic implements Serializable{

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
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataInici() {
        return dataInici;
    }
    public void setDataInici(Date dataInici) {
        this.dataInici = dataInici;
    }

    public Date getDataFi() {
        return dataFi;
    }
    public void setDataFi(Date dataFi) {
        this.dataFi = dataFi;
    }

    public String getComentaris() {
        return comentaris;
    }
    public void setComentaris(String comentaris) {
        this.comentaris = comentaris;
    }

    public Tasca getTasca() {
        return tasca;
    }
    public void setTasca(Tasca tasca) {
        this.tasca = tasca;
    }
}