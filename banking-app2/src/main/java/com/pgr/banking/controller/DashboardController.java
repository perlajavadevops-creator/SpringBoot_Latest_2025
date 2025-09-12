package com.pgr.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pgr.banking.entity.User;
import com.pgr.banking.service.AccountService;
import com.pgr.banking.service.TransactionService;
import com.pgr.banking.service.UserService;

@Controller
public class DashboardController {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/")
	public String home() {
		return "redirect:/dashboard";
	}

	@GetMapping("/dashboard")
	public String dashboard(Authentication authentication, Model model) {
		User user = userService.getCurrentUser(authentication.getName());

		model.addAttribute("user", user);
		model.addAttribute("accounts", accountService.getUserAccounts(user));
		model.addAttribute("recentTransactions",
				transactionService.getUserTransactions(user).stream().limit(5).toList());

		return "dashboard";
	}
}