package org.example.tpremise.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime date;
    
    @Column(nullable = false, name = "montant_avant")
    private double montantAvant;
    
    @Column(nullable = false, name = "montant_apres")
    private double montantApres;

    @ManyToOne
    @JoinColumn(name = "remise_id")
    private Remise remise;
}