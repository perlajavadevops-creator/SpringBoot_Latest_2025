package com.pgr.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pgr.mvc.dto.Login;
import com.pgr.mvc.dto.ResetPasswordDTO;
import com.pgr.mvc.service.UserService;

@Controller
public class ForgotpasswordController {
	@Autowired
	UserService userService;
	
	
	@GetMapping("/forgotpassword")
	public ModelAndView showForgotPassword() {
		return new ModelAndView("forgotpassword", "login", new Login());
	}
	@PostMapping("/forgotpassword")
	public String resetPassword(@ModelAttribute ResetPasswordDTO dto,
	                            Model model) {

	    if (!dto.getPassword().equals(dto.getConfirmPassword())) {
	        model.addAttribute("message", "Passwords do not match!");
	        model.addAttribute("messageType", "error");
	        return "forgotpassword";
	    }

	    int rowsUpdated = userService.updatePassword(dto.getUsername(), dto.getPassword());

	    if (rowsUpdated > 0) {
	        model.addAttribute("message", "Password updated successfully. Please login.");
	        model.addAttribute("messageType", "success");
	    } else {
	        model.addAttribute("message", "User not found!");
	        model.addAttribute("messageType", "error");
	    }

	    return "forgotpassword";
	}
	
	
}
