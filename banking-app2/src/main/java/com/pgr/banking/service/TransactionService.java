package com.pgr.banking.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pgr.banking.dto.FundRequest;
import com.pgr.banking.dto.TransferRequest;
import com.pgr.banking.entity.Account;
import com.pgr.banking.entity.Transaction;
import com.pgr.banking.entity.User;
import com.pgr.banking.repository.TransactionRepository;

@Service
@Transactional
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	public Transaction addFunds(FundRequest request) {
		Account account = accountService.getAccountByNumber(request.getAccountNumber());

		if (!account.isActive()) {
			throw new RuntimeException("Account is not active");
		}

		String transactionId = generateTransactionId();
		Transaction transaction = new Transaction(transactionId, request.getAmount(),
				Transaction.TransactionType.FUND_ADDITION,
				request.getDescription() != null ? request.getDescription() : "Fund addition");

		transaction.setToAccount(account);
		account.setBalance(account.getBalance().add(request.getAmount()));

		transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
		Transaction savedTransaction = transactionRepository.save(transaction);

		// Send real-time updates
		accountService.sendBalanceUpdate(account);
		sendTransactionNotification(account.getUser(), "Funds added: $" + request.getAmount());

		return savedTransaction;
	}

	public Transaction transferFunds(TransferRequest request) {
		Account fromAccount = accountService.getAccountByNumber(request.getFromAccountNumber());
		Account toAccount = accountService.getAccountByNumber(request.getToAccountNumber());

		if (!fromAccount.isActive() || !toAccount.isActive()) {
			throw new RuntimeException("One or both accounts are not active");
		}

		if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
			throw new RuntimeException("Insufficient balance");
		}

		if (fromAccount.getAccountNumber().equals(toAccount.getAccountNumber())) {
			throw new RuntimeException("Cannot transfer to the same account");
		}

		String transactionId = generateTransactionId();
		Transaction transaction = new Transaction(transactionId, request.getAmount(),
				Transaction.TransactionType.TRANSFER,
				request.getDescription() != null ? request.getDescription() : "Fund transfer");

		transaction.setFromAccount(fromAccount);
		transaction.setToAccount(toAccount);

		// Update balances
		fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
		toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));

		transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
		Transaction savedTransaction = transactionRepository.save(transaction);

		// Send real-time updates
		accountService.sendBalanceUpdate(fromAccount);
		accountService.sendBalanceUpdate(toAccount);
		sendTransactionNotification(fromAccount.getUser(), "Transfer sent: $" + request.getAmount());
		sendTransactionNotification(toAccount.getUser(), "Transfer received: $" + request.getAmount());

		return savedTransaction;
	}

	public List<Transaction> getUserTransactions(User user) {
		return transactionRepository.findByUserId(user.getId());
	}

	public List<Transaction> getAccountTransactions(Account account) {
		return transactionRepository.findByAccount(account);
	}

	private String generateTransactionId() {
		return "TXN" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
	}

	private void sendTransactionNotification(User user, String message) {
		messagingTemplate.convertAndSendToUser(user.getUsername(), "/topic/transaction-notification", message);
	}
}
