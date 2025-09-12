package com.pgr.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pgr.banking.entity.Account;
import com.pgr.banking.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount = :account OR t.toAccount = :account ORDER BY t.createdAt DESC")
    List<Transaction> findByAccountOrderByCreatedAtDesc(@Param("account") Account account);
    
    List<Transaction> findByTransactionId(String transactionId);
}
