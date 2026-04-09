package org.example.tpremise.JDBC;

import org.example.tpremise.exception.RemiseException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Service
public class RemiseJdbcService {

    private final JdbcTemplate jdbcTemplate;

    public RemiseJdbcService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Remise create(double montantMin, double montantMax, double taux) {
        if (montantMax < montantMin || taux < 0) {
            throw new RemiseException("Parametres de remise invalides");
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO REMISE (montant_min, montant_max, taux) VALUES (?, ?, ?)",
                    new String[]{"id"}
            );
            ps.setDouble(1, montantMin);
            ps.setDouble(2, montantMax);
            ps.setDouble(3, taux);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RemiseException("Creation de remise impossible");
        }

        Remise remise = new Remise();
        remise.setId(key.longValue());
        remise.setMontantMin(montantMin);
        remise.setMontantMax(montantMax);
        remise.setTaux(taux);
        return remise;
    }

    public Optional<Remise> findByMontant(double montant) {
        List<Remise> remises = jdbcTemplate.query(
                "SELECT id, montant_min, montant_max, taux FROM REMISE WHERE ? BETWEEN montant_min AND montant_max LIMIT 1",
                (rs, rowNum) -> {
                    Remise remise = new Remise();
                    remise.setId(rs.getLong("id"));
                    remise.setMontantMin(rs.getDouble("montant_min"));
                    remise.setMontantMax(rs.getDouble("montant_max"));
                    remise.setTaux(rs.getDouble("taux"));
                    return remise;
                },
                montant
        );
        return remises.stream().findFirst();
    }

    public Optional<Remise> findById(Long id) {
        List<Remise> remises = jdbcTemplate.query(
                "SELECT id, montant_min, montant_max, taux FROM REMISE WHERE id = ?",
                (rs, rowNum) -> {
                    Remise remise = new Remise();
                    remise.setId(rs.getLong("id"));
                    remise.setMontantMin(rs.getDouble("montant_min"));
                    remise.setMontantMax(rs.getDouble("montant_max"));
                    remise.setTaux(rs.getDouble("taux"));
                    return remise;
                },
                id
        );
        return remises.stream().findFirst();
    }
}

