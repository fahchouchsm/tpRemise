package org.example.tpremise.JDBC;

import org.example.tpremise.service.ReductionService;
import org.example.tpremise.exception.RemiseException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReductionDBService implements ReductionService {

    private final JdbcTemplate jdbcTemplate;

    public ReductionDBService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public double calculerRemise(double montant) {
        if (montant <= 0) {
            throw new RemiseException("Le montant doit etre superieur a 0");
        }

        List<Double> taux = jdbcTemplate.query(
                "SELECT taux FROM REMISE WHERE ? BETWEEN montant_min AND montant_max LIMIT 1",
                (rs, rowNum) -> rs.getDouble("taux"),
                montant
        );

        if (taux.isEmpty()) {
            throw new RemiseException("Aucune tranche de remise trouve pour le montant: " + montant);
        }

        return montant * taux.getFirst();
    }
}
