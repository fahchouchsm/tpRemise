package org.example.tpremise.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Remise {
    private Long id;
    private double montantMin;
    private double montantMax;
    private double taux;
}
