package org.example.tpremise.JDBC;

import java.sql.Timestamp;

public class Transaction {
    private Long id;
    private Timestamp date;
    private double montantAvant;
    private double montantApres;
    private Long remiseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public double getMontantAvant() {
        return montantAvant;
    }

    public void setMontantAvant(double montantAvant) {
        this.montantAvant = montantAvant;
    }

    public double getMontantApres() {
        return montantApres;
    }

    public void setMontantApres(double montantApres) {
        this.montantApres = montantApres;
    }

    public Long getRemiseId() {
        return remiseId;
    }

    public void setRemiseId(Long remiseId) {
        this.remiseId = remiseId;
    }
}
