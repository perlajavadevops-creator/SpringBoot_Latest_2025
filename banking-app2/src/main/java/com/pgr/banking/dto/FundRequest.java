package com.pgr.banking.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FundRequest {
    @NotBlank(message = "Account number is required")
    private String accountNumber;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    private String description;
    
    // Constructors
    public FundRequest() {}
    
    public FundRequest(String accountNumber, BigDecimal amount, String description) {
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.description = description;
    }
    
    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}