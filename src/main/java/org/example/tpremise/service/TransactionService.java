package org.example.tpremise.service;

import org.example.tpremise.exception.RemiseException;
import org.example.tpremise.persistence.Remise;
import org.example.tpremise.persistence.Transaction;
import org.example.tpremise.persistence.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction save(double montantAvant, double montantApres, Remise remise) {
        if (montantAvant <= 0 || montantApres <= 0) {
            throw new RemiseException("Les montants doivent être supérieurs à 0");
        }
        
        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.now());
        transaction.setMontantAvant(montantAvant);
        transaction.setMontantApres(montantApres);
        transaction.setRemise(remise);
        
        return transactionRepository.save(transaction);
    }

    public Transaction update(Long id, double montantAvant, double montantApres, Remise remise) {
        if (montantAvant <= 0 || montantApres <= 0) {
            throw new RemiseException("Les montants doivent être supérieurs à 0");
        }
        
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isEmpty()) {
            throw new RemiseException("Transaction non trouvée: " + id);
        }
        
        Transaction transaction = transactionOptional.get();
        transaction.setMontantAvant(montantAvant);
        transaction.setMontantApres(montantApres);
        transaction.setRemise(remise);
        
        return transactionRepository.save(transaction);
    }

    public void deleteById(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new RemiseException("Transaction non trouvée: " + id);
        }
        transactionRepository.deleteById(id);
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
}

