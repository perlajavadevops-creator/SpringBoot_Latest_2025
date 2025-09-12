package com.pgr.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pgr.mvc.dto.ForgotPasswordDto;
import com.pgr.mvc.dto.Login;
import com.pgr.mvc.service.LoginService;

@Controller
@RequestMapping
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@GetMapping("/")
	public ModelAndView indexPage() {
		ModelAndView modelAndView = new ModelAndView("index");

		return modelAndView;
	}
	
	@GetMapping("/user-register")
	public ModelAndView userRegisterPage() {
		ModelAndView modelAndView = new ModelAndView("user-register");

		return modelAndView;
	}
	
	
	@GetMapping("/forgot-password-page")
	public ModelAndView forgotPasswordPage() {
		ModelAndView modelAndView = new ModelAndView("forgot-password");

		return modelAndView;
	}

	@PostMapping("/login")
	public String login(@ModelAttribute Login login, Model model) {
		
		String messsage = loginService.getLoginDetails(login);
		model.addAttribute("message",messsage);
		return "dashboard";
	}
	
	@PostMapping("/register")
	public String userRegistration(@ModelAttribute Login login, Model model) {
		
		boolean flag = loginService.userRegistration(login);
		model.addAttribute("message","User successfully Registered");
		return "user-register";
	}
	

	@PostMapping("/forgot-password")
	public String userForgotPassword(@ModelAttribute ForgotPasswordDto forgotPasswordDto, Model model) {
		
		boolean flag = loginService.userForgotPassword(forgotPasswordDto);
		model.addAttribute("message","Password succesfully updated.....");
		return "forgot-password";
	}
	
}