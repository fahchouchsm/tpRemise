package org.example.tpremise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Transaction {
    private Long id;

    private LocalDateTime date;

    private double montantAvant;

    private double montantApres;

    private Remise remise;

    public Transaction() {}

    public Transaction(double montantAvant, double montantApres, Remise remise) {
        this.setDate(LocalDateTime.now());
        this.setMontantAvant(montantAvant);
        this.setMontantApres(montantApres);
        this.setRemise(remise);
    }
}
