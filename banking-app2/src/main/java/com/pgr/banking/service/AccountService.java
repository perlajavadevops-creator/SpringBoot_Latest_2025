package com.pgr.banking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.pgr.banking.entity.Account;
import com.pgr.banking.entity.User;
import com.pgr.banking.repository.AccountRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    public Account createAccount(User user, Account.AccountType accountType) {
        String accountNumber = generateAccountNumber();
        Account account = new Account(accountNumber, accountType, user);
        Account savedAccount = accountRepository.save(account);
        
        // Send real-time notification
        messagingTemplate.convertAndSendToUser(
            user.getUsername(),
            "/topic/account-created",
            "Account " + accountNumber + " created successfully"
        );
        
        return savedAccount;
    }
    
    public Account activateAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        
        if (account.getStatus() != Account.AccountStatus.PENDING) {
            throw new RuntimeException("Account is not in pending status");
        }
        
        account.setStatus(Account.AccountStatus.ACTIVE);
        account.setActivatedAt(LocalDateTime.now());
        Account activatedAccount = accountRepository.save(account);
        
        // Send real-time notification
        messagingTemplate.convertAndSendToUser(
            account.getUser().getUsername(),
            "/topic/account-activated",
            "Account " + accountNumber + " activated successfully"
        );
        
        return activatedAccount;
    }
    
    public List<Account> getUserAccounts(User user) {
        return accountRepository.findByUser(user);
    }
    
    public List<Account> getActiveUserAccounts(User user) {
        return accountRepository.findByUserAndStatus(user, Account.AccountStatus.ACTIVE);
    }
    
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
    
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));
    }
    
    public void sendBalanceUpdate(Account account) {
        messagingTemplate.convertAndSendToUser(
            account.getUser().getUsername(),
            "/topic/balance-update",
            account.getAccountNumber() + ":" + account.getBalance()
        );
    }
    
    private String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = "ACC" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }
}