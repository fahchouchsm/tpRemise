package org.example.tpremise.controllers;

import org.example.tpremise.DTO.CreateTransactionDTO;
import org.example.tpremise.DTO.TransactionPutDTO;
import org.example.tpremise.models.Transaction;
import org.example.tpremise.service.TransactionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class RemiseController {
    private final TransactionService transactionService;

    public RemiseController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction")
    public void remise(@RequestBody CreateTransactionDTO dto)  {
        transactionService.addTransaction(dto);
    }

    @GetMapping("/transaction/{id}")
    public Transaction get(@PathVariable String id) {
        return transactionService.getTransactionById(id);
    }

    @DeleteMapping("/transaction/{id}")
    public void delete(@PathVariable String id) {
        transactionService.deleteTransactionById(id);
    }

    @PutMapping("/transaction/{id}")
    public void update(@RequestBody @Validated TransactionPutDTO dto, @PathVariable String id) {
        transactionService.updateTransaction(dto, id);
    }
}
