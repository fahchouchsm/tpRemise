package org.example.tpremise.repositories;

import org.example.tpremise.mappers.RemiseMapper;
import org.example.tpremise.models.Remise;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RemiseRepository {
    private final JdbcTemplate jdbcTemplate;

    public RemiseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Double getTaux(double montant) {
        return jdbcTemplate.queryForObject(
                "SELECT taux FROM REMISE WHERE ? BETWEEN montant_min AND montant_max LIMIT 1",
                Double.class,
                montant
        );
    }

    @Transactional
    public Remise getRemise(double montant) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM REMISE WHERE montant_min <= ? AND montant_max >= ? LIMIT 1",
                    new RemiseMapper(),
                    montant,
                    montant
            );
        } catch (Exception e) {
            return null;
        }
    }

}
