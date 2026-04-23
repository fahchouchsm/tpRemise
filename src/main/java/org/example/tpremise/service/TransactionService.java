package org.example.tpremise.service;

import org.example.tpremise.DTO.CreateTransactionDTO;
import org.example.tpremise.DTO.TransactionPutDTO;
import org.example.tpremise.models.Remise;
import org.example.tpremise.models.Transaction;
import org.example.tpremise.repositories.RemiseRepository;
import org.example.tpremise.repositories.TransactionRepository;
import org.example.tpremise.service.reductionServices.ReductionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransactionService {
    private final ReductionService reductionService;
    private final RemiseRepository remiseRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(@Qualifier("reductionDBService") ReductionService reductionService, RemiseRepository remiseRepository, TransactionRepository transactionRepository) {
        this.reductionService = reductionService;
        this.remiseRepository = remiseRepository;
        this.transactionRepository = transactionRepository;

    }

    public void addTransaction(CreateTransactionDTO dto) {
        double montant = dto.getMontant();
        double apresRemise = reductionService.calculerRemise(montant);
        Remise remise = remiseRepository.getRemise(montant);
        Transaction transaction =  new Transaction(montant, apresRemise, remise);
        transactionRepository.addTransaction(transaction);
    }

    public void updateTransaction(TransactionPutDTO dto, String id) {
        Transaction transaction = transactionRepository.getTransactionById(id);
        if (transaction == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Transaction not found"
            );
        }

        transactionRepository.updateTransactionById(id, dto);
    }

    public Transaction getTransactionById(String id) {
        Transaction transaction = transactionRepository.getTransactionById(id);
        if (transaction == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Transaction not found"
            );
        }
        return transaction;
    }

    public void deleteTransactionById(String id) {
        int rows = transactionRepository.deleteTransactionById(id);
        if (rows == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Transaction not found"
            );
        }
    }
}
