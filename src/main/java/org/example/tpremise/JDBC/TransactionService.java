package org.example.tpremise.JDBC;

import org.example.tpremise.exception.RemiseException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final JdbcTemplate jdbcTemplate;

    public TransactionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Transaction save(double montantAvant, double montantApres, Remise remise) {
        validateMontants(montantAvant, montantApres);
        if (remise == null || remise.getId() == null) {
            throw new RemiseException("La remise associee est obligatoire");
        }

        Transaction transaction = new Transaction();
        transaction.setDate(Timestamp.from(Instant.now()));
        transaction.setMontantAvant(montantAvant);
        transaction.setMontantApres(montantApres);
        transaction.setRemiseId(remise.getId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO \"TRANSACTION\"(date, montant_avant, montant_apres, remise_id) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setTimestamp(1, transaction.getDate());
            ps.setDouble(2, transaction.getMontantAvant());
            ps.setDouble(3, transaction.getMontantApres());
            ps.setLong(4, transaction.getRemiseId());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RemiseException("Creation de transaction impossible");
        }
        transaction.setId(key.longValue());
        return transaction;
    }

    public Transaction update(Long id, double montantAvant, double montantApres, Remise remise) {
        validateMontants(montantAvant, montantApres);
        if (id == null || findById(id).isEmpty()) {
            throw new RemiseException("Transaction non trouvee: " + id);
        }

        long remiseId = remise != null && remise.getId() != null ? remise.getId() : 0L;
        if (remiseId == 0L) {
            throw new RemiseException("La remise associee est obligatoire");
        }

        jdbcTemplate.update(
                "UPDATE \"TRANSACTION\" SET montant_avant = ?, montant_apres = ?, remise_id = ? WHERE id = ?",
                montantAvant,
                montantApres,
                remiseId,
                id
        );

        return findById(id).orElseThrow(() -> new RemiseException("Transaction non trouvee: " + id));
    }

    public void deleteById(Long id) {
        if (id == null || findById(id).isEmpty()) {
            throw new RemiseException("Transaction non trouvee: " + id);
        }
        jdbcTemplate.update("DELETE FROM \"TRANSACTION\" WHERE id = ?", id);
    }

    public Optional<Transaction> findById(Long id) {
        List<Transaction> transactions = jdbcTemplate.query(
                "SELECT id, date, montant_avant, montant_apres, remise_id FROM \"TRANSACTION\" WHERE id = ?",
                (rs, rowNum) -> {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getLong("id"));
                    transaction.setDate(rs.getTimestamp("date"));
                    transaction.setMontantAvant(rs.getDouble("montant_avant"));
                    transaction.setMontantApres(rs.getDouble("montant_apres"));
                    transaction.setRemiseId(rs.getLong("remise_id"));
                    return transaction;
                },
                id
        );
        return transactions.stream().findFirst();
    }

    private void validateMontants(double montantAvant, double montantApres) {
        if (montantAvant <= 0 || montantApres <= 0) {
            throw new RemiseException("Les montants doivent etre superieurs a 0");
        }
    }
}
