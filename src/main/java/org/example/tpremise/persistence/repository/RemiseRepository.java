package org.example.tpremise.persistence.repository;

import org.example.tpremise.persistence.Remise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface RemiseRepository extends JpaRepository<Remise, Long> {
    @Query("SELECT r FROM Remise r WHERE :montant BETWEEN r.montantMin AND r.montantMax")
    Optional<Remise> findByMontant(double montant);
}