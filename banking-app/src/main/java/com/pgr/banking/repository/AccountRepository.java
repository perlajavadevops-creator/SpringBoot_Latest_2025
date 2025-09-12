package com.pgr.banking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pgr.banking.entity.Account;
import com.pgr.banking.entity.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findByUserAndActiveTrue(User user);
    List<Account> findByUser(User user);
}
