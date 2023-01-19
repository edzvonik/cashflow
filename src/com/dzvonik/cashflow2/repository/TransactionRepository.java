package com.dzvonik.cashflow2.repository;

import com.dzvonik.cashflow2.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
