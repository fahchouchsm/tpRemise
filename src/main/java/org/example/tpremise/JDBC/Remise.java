package org.example.tpremise.JDBC;

public class Remise {
    private Long id;
    private double montantMin;
    private double montantMax;
    private double taux;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMontantMin() {
        return montantMin;
    }

    public void setMontantMin(double montantMin) {
        this.montantMin = montantMin;
    }

    public double getMontantMax() {
        return montantMax;
    }

    public void setMontantMax(double montantMax) {
        this.montantMax = montantMax;
    }

    public double getTaux() {
        return taux;
    }

    public void setTaux(double taux) {
        this.taux = taux;
    }
}

