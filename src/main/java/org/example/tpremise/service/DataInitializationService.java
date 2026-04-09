package org.example.tpremise.service;

import org.example.tpremise.persistence.Remise;
import org.example.tpremise.persistence.repository.RemiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

@Service
public class DataInitializationService {
    private final RemiseRepository remiseRepository;

    @Autowired
    public DataInitializationService(RemiseRepository remiseRepository) {
        this.remiseRepository = remiseRepository;
    }

    @PostConstruct
    public void initializeData() {
        // Initialiser les données de remise si la table est vide
        if (remiseRepository.count() == 0) {
            Remise remise1 = new Remise();
            remise1.setMontantMin(0);
            remise1.setMontantMax(1000);
            remise1.setTaux(0.02);
            remiseRepository.save(remise1);

            Remise remise2 = new Remise();
            remise2.setMontantMin(1001);
            remise2.setMontantMax(5000);
            remise2.setTaux(0.05);
            remiseRepository.save(remise2);

            Remise remise3 = new Remise();
            remise3.setMontantMin(5001);
            remise3.setMontantMax(100000);
            remise3.setTaux(0.10);
            remiseRepository.save(remise3);
        }
    }
}

