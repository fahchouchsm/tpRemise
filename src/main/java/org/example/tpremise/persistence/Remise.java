package org.example.tpremise.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "REMISE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Remise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "montant_min", nullable = false)
    private double montantMin;
    
    @Column(name = "montant_max", nullable = false)
    private double montantMax;
    
    @Column(nullable = false)
    private double taux;
    
    @OneToMany(mappedBy = "remise", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}