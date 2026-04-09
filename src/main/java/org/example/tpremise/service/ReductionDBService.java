package org.example.tpremise.service;

import org.example.tpremise.exception.RemiseException;
import org.example.tpremise.persistence.Remise;
import org.example.tpremise.persistence.repository.RemiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReductionDBService implements ReductionService {
    private final RemiseRepository remiseRepository;

    @Autowired
    public ReductionDBService(RemiseRepository remiseRepository) {
        this.remiseRepository = remiseRepository;
    }

    @Override
    public double calculerRemise(double montant) {
        if (montant <= 0) {
            throw new RemiseException("Le montant doit être supérieur à 0");
        }
        
        Remise remise = remiseRepository.findByMontant(montant)
                .orElseThrow(() -> new RemiseException("Aucune remise trouvée pour le montant: " + montant));
        
        return montant * remise.getTaux();
    }
}

