package com.pgr.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pgr.banking.dto.UserRegistrationDto;
import com.pgr.banking.entity.User;
import com.pgr.banking.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		if (error != null) {
			model.addAttribute("error", "Invalid username or password");
		}
		if (logout != null) {
			model.addAttribute("message", "You have been logged out successfully");
		}
		return "login";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("userRegistrationDto", new UserRegistrationDto());
		return "register";
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute UserRegistrationDto registrationDto, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {

		if (bindingResult.hasErrors()) {
			return "register";
		}

		try {
			User user = userService.registerUser(registrationDto);
			redirectAttributes.addFlashAttribute("success",
					"Registration successful! You can now login with username: " + user.getUsername());
			return "redirect:/login";
		} catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			return "register";
		}
	}
}