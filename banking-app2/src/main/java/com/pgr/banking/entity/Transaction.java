package com.pgr.banking.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String transactionId;

	@NotNull
	@DecimalMin(value = "0.01")
	@Column(precision = 15, scale = 2)
	private BigDecimal amount;

	@Enumerated(EnumType.STRING)
	private TransactionType type;

	@Enumerated(EnumType.STRING)
	private TransactionStatus status;

	private String description;
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_account_id")
	private Account fromAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_account_id")
	private Account toAccount;

	public enum TransactionType {
		DEPOSIT, WITHDRAWAL, TRANSFER, FUND_ADDITION
	}

	public enum TransactionStatus {
		PENDING, COMPLETED, FAILED, CANCELLED
	}

	// Constructors
	public Transaction() {
		this.createdAt = LocalDateTime.now();
		this.status = TransactionStatus.PENDING;
	}

	public Transaction(String transactionId, BigDecimal amount, TransactionType type, String description) {
		this();
		this.transactionId = transactionId;
		this.amount = amount;
		this.type = type;
		this.description = description;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Account getToAccount() {
		return toAccount;
	}

	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}
}