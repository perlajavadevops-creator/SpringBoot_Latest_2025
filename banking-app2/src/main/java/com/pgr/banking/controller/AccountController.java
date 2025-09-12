package com.pgr.banking.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pgr.banking.entity.Account;
import com.pgr.banking.entity.User;
import com.pgr.banking.service.AccountService;
import com.pgr.banking.service.TransactionService;
import com.pgr.banking.service.UserService;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TransactionService transactionService;
    
    @GetMapping("/create")
    public String showCreateAccountForm(Model model) {
        model.addAttribute("accountTypes", Account.AccountType.values());
        return "create-account";
    }
    
    @PostMapping("/create")
    public String createAccount(@RequestParam("accountType") Account.AccountType accountType,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getCurrentUser(authentication.getName());
            Account account = accountService.createAccount(user, accountType);
            
            redirectAttributes.addFlashAttribute("success", 
                "Account created successfully! Account Number: " + account.getAccountNumber());
            return "redirect:/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error creating account: " + e.getMessage());
            return "redirect:/accounts/create";
        }
    }
    
    @GetMapping("/activate")
    public String showActivateAccountForm(Authentication authentication, Model model) {
        User user = userService.getCurrentUser(authentication.getName());
        List<Account> pendingAccounts = user.getAccounts().stream()
                .filter(account -> account.getStatus() == Account.AccountStatus.PENDING)
                .toList();
        
        model.addAttribute("pendingAccounts", pendingAccounts);
        return "activate-account";
    }
    
    @PostMapping("/activate")
    public String activateAccount(@RequestParam("accountNumber") String accountNumber,
                                 RedirectAttributes redirectAttributes) {
        try {
            Account account = accountService.activateAccount(accountNumber);
            redirectAttributes.addFlashAttribute("success", 
                "Account " + account.getAccountNumber() + " activated successfully!");
            return "redirect:/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error activating account: " + e.getMessage());
            return "redirect:/accounts/activate";
        }
    }
    
    @GetMapping("/{accountNumber}/transactions")
    public String viewAccountTransactions(@PathVariable String accountNumber,
                                        Authentication authentication,
                                        Model model) {
        try {
            User user = userService.getCurrentUser(authentication.getName());
            Account account = accountService.getAccountByNumber(accountNumber);
            
            // Verify account belongs to current user
            if (!account.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Access denied");
            }
            
            model.addAttribute("account", account);
            model.addAttribute("transactions", transactionService.getAccountTransactions(account));
            return "transaction-history";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "dashboard";
        }
    }
}