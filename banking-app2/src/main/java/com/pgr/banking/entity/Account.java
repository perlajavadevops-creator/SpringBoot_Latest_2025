package com.pgr.banking.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String accountNumber;

	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
	@Column(precision = 15, scale = 2)
	private BigDecimal balance;

	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	private LocalDateTime createdAt;
	private LocalDateTime activatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL)
	private List<Transaction> outgoingTransactions;

	@OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL)
	private List<Transaction> incomingTransactions;

	public enum AccountType {
		SAVINGS, CHECKING, CURRENT
	}

	public enum AccountStatus {
		PENDING, ACTIVE, SUSPENDED, CLOSED
	}

	// Constructors
	public Account() {
		this.createdAt = LocalDateTime.now();
		this.balance = BigDecimal.ZERO;
		this.status = AccountStatus.PENDING;
	}

	public Account(String accountNumber, AccountType accountType, User user) {
		this();
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.user = user;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getActivatedAt() {
		return activatedAt;
	}

	public void setActivatedAt(LocalDateTime activatedAt) {
		this.activatedAt = activatedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Transaction> getOutgoingTransactions() {
		return outgoingTransactions;
	}

	public void setOutgoingTransactions(List<Transaction> outgoingTransactions) {
		this.outgoingTransactions = outgoingTransactions;
	}

	public List<Transaction> getIncomingTransactions() {
		return incomingTransactions;
	}

	public void setIncomingTransactions(List<Transaction> incomingTransactions) {
		this.incomingTransactions = incomingTransactions;
	}

	public boolean isActive() {
		return status == AccountStatus.ACTIVE;
	}
}