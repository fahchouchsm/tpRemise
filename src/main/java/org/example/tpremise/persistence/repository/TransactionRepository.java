package org.example.tpremise.persistence.repository;

import org.example.tpremise.persistence.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {}