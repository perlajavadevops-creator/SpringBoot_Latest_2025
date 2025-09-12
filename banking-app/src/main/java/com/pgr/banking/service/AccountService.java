package com.pgr.banking.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgr.banking.dto.AccountResponse;
import com.pgr.banking.entity.Account;
import com.pgr.banking.entity.User;
import com.pgr.banking.exception.AccountNotFoundException;
import com.pgr.banking.repository.AccountRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService {

   /* @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(User user, Account.AccountType accountType) {
        String accountNumber = generateAccountNumber();
        Account account = new Account(accountNumber, accountType, user);
        return accountRepository.save(account);
    }

    public List<AccountResponse> getUserAccounts(User user) {
        List<Account> accounts = accountRepository.findByUser(user);
        return accounts.stream()
                .map(AccountResponse::new)
                .collect(Collectors.toList());
    }

    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));
    }

    public Account addFunds(String accountNumber, BigDecimal amount) {
        Account account = findByAccountNumber(accountNumber);
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    public Account deductFunds(String accountNumber, BigDecimal amount) {
        Account account = findByAccountNumber(accountNumber);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public Account activateAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        account.setActive(true);
        return accountRepository.save(account);
    }*/
}

