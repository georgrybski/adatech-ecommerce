package com.monolithic.ecommerce.repository;

import com.monolithic.ecommerce.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
