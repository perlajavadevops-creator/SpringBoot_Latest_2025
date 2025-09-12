package com.pgr.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.pgr.banking.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private Long id;
    private String accountNumber;
    private Account.AccountType accountType;
    private BigDecimal balance;
    private boolean active;
    private LocalDateTime createdAt;
}
