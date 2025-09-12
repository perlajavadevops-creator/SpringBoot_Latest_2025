package com.pgr.banking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pgr.banking.entity.Account;
import com.pgr.banking.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionId(String transactionId);
    
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount = :account OR t.toAccount = :account ORDER BY t.createdAt DESC")
    List<Transaction> findByAccount(@Param("account") Account account);
    
    List<Transaction> findByFromAccountOrToAccountOrderByCreatedAtDesc(Account fromAccount, Account toAccount);
    
    @Query("SELECT t FROM Transaction t WHERE (t.fromAccount.user.id = :userId OR t.toAccount.user.id = :userId) ORDER BY t.createdAt DESC")
    List<Transaction> findByUserId(@Param("userId") Long userId);
}