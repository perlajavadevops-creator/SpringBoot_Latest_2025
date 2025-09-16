package com.pgr.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pgr.mvc.dto.Login;
import com.pgr.mvc.entity.LoginEntity;
import com.pgr.mvc.service.LoginService;
import com.pgr.mvc.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	LoginService loginService;

	@GetMapping("/")
	public ModelAndView indexPage() {
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}

	@PostMapping("/login")
	public String login(@ModelAttribute Login login, Model model, HttpSession httpSession) {
		
		String message = loginService.getLoginDetails(login);
		model.addAttribute("message", message);
		if (message.equals("Success")) {
			httpSession.setAttribute("username", login.getUsername());
			return "dashboard";
		} else {
			return "login";
		}

	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate(); // Ends the session
		ModelAndView modelAndView = new ModelAndView("login"); // Redirect to login page
		modelAndView.addObject("message", "You have been logged out successfully.");
		return modelAndView;
	}

	@GetMapping("/register")
	public ModelAndView showRegisterForm() {
		return new ModelAndView("register", "login", new Login());
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute Login login, Model model) {
		String message = loginService.registerUser(login);
		model.addAttribute("message", message);
		return "register";
	}

	@GetMapping("/profile")
	public String showProfile(Model model) {
		// Add user data to the model if needed
		List<LoginEntity> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "userform"; // This loads profile.html from /templates
	}

}
