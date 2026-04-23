package org.example.tpremise.repositories;

import org.example.tpremise.DTO.TransactionPutDTO;
import org.example.tpremise.mappers.TransactionMapper;
import org.example.tpremise.models.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addTransaction(Transaction transaction) {
        jdbcTemplate.update(
                "INSERT INTO TRANSACTION (date, montant_avant, montant_apres, remise_id) VALUES (?, ?, ?, ?)",
                transaction.getDate(),
                transaction.getMontantAvant(),
                transaction.getMontantApres(),
                transaction.getRemise().getId()
        );
    }

    public Transaction getTransactionById(String id) {
        return jdbcTemplate.query(
                "SELECT * FROM TRANSACTION WHERE id = ?",
                new TransactionMapper(),
                id
        ).get(0);
    }

    public int deleteTransactionById(String id) {
        return jdbcTemplate.update(
                "DELETE * FROM FROM TRANSACTION WHERE id = ?",
                id
        );
    }

    public void updateTransactionById(String id, TransactionPutDTO dto) {
        jdbcTemplate.update(
                "UPDATE TRANSACTION SET date = ?, montant_avant = ?, montant_apres = ?, remise_id = ? WHERE id = ?",
                dto.getDate(),
                dto.getMontantAvant(),
                dto.getMontantApres(),
                dto.getRemiseId(),
                id
        );
    }

}
