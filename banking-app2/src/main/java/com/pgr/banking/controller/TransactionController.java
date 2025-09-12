package com.pgr.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pgr.banking.dto.FundRequest;
import com.pgr.banking.dto.TransferRequest;
import com.pgr.banking.entity.Account;
import com.pgr.banking.entity.Transaction;
import com.pgr.banking.entity.User;
import com.pgr.banking.service.AccountService;
import com.pgr.banking.service.TransactionService;
import com.pgr.banking.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/transactions")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/add-funds")
    public String showAddFundsForm(Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication.getName());
        List<Account> activeAccounts = accountService.getActiveUserAccounts(user);
        
        model.addAttribute("activeAccounts", activeAccounts);
        model.addAttribute("fundRequest", new FundRequest());
        return "add-funds";
    }
    
    @PostMapping("/add-funds")
    public String addFunds(@Valid @ModelAttribute FundRequest fundRequest,
                          BindingResult bindingResult,
                          Authentication authentication,
                          RedirectAttributes redirectAttributes,
                          Model model) {
        
        if (bindingResult.hasErrors()) {
            User user = userService.getCurrentUser(authentication.getName());
            model.addAttribute("activeAccounts", accountService.getActiveUserAccounts(user));
            return "add-funds";
        }
        
        try {
            Transaction transaction = transactionService.addFunds(fundRequest);
            redirectAttributes.addFlashAttribute("success", 
                "Funds added successfully! Transaction ID: " + transaction.getTransactionId());
            return "redirect:/dashboard";
        } catch (Exception e) {
            User user = userService.getCurrentUser(authentication.getName());
            model.addAttribute("activeAccounts", accountService.getActiveUserAccounts(user));
            model.addAttribute("error", e.getMessage());
            return "add-funds";
        }
    }
    
    @GetMapping("/transfer")
    public String showTransferForm(Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication.getName());
        List<Account> activeAccounts = accountService.getActiveUserAccounts(user);
        
        model.addAttribute("activeAccounts", activeAccounts);
        model.addAttribute("transferRequest", new TransferRequest());
        return "transfer-funds";
    }
    
    @PostMapping("/transfer")
    public String transferFunds(@Valid @ModelAttribute TransferRequest transferRequest,
                               BindingResult bindingResult,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        
        if (bindingResult.hasErrors()) {
            User user = userService.getCurrentUser(authentication.getName());
            model.addAttribute("activeAccounts", accountService.getActiveUserAccounts(user));
            return "transfer-funds";
        }
        
        try {
            Transaction transaction = transactionService.transferFunds(transferRequest);
            redirectAttributes.addFlashAttribute("success", 
                "Transfer completed successfully! Transaction ID: " + transaction.getTransactionId());
            return "redirect:/dashboard";
        } catch (Exception e) {
            User user = userService.getCurrentUser(authentication.getName());
            model.addAttribute("activeAccounts", accountService.getActiveUserAccounts(user));
            model.addAttribute("error", e.getMessage());
            return "transfer-funds";
        }
    }
    
    @GetMapping("/history")
    public String viewTransactionHistory(Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication.getName());
        List<Transaction> transactions = transactionService.getUserTransactions(user);
        
        model.addAttribute("transactions", transactions);
        return "transaction-history";
    }
}
