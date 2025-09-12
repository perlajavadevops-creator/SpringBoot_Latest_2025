package com.pgr.mvc.Employee_App.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pgr.mvc.Employee_App.dto.Login;
import com.pgr.mvc.Employee_App.service.LoginService;

import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {
	@Autowired
	
	LoginService loginService;
	@GetMapping("/")
	public ModelAndView indexPage() {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("login", new Login()); 
		return modelAndView;
	}
	@PostMapping("/login")
	public String login(@ModelAttribute("login") Login login, Model model) {
		
		String messsage = loginService.getLoginDetails(login);
		model.addAttribute("message",messsage);
		return "home";
	}
	@PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destroys the session
        return "redirect:/";  // Redirects to login page (index.jsp or index.html)
    }
	@GetMapping("/register")
	public ModelAndView userRegisterPage() {
		ModelAndView modelAndView = new ModelAndView("register");
		modelAndView.addObject("login", new Login());

		return modelAndView;
	}

    @PostMapping("/register")
    public String registerUser( @ModelAttribute("login") Login login,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            return "register"; // Redisplay form with validation errors
        }

        boolean success = loginService.userRegistration(login);
        if (success) {
            return "redirect:/?registered"; // Redirect to login page with success flag
        } else {
            model.addAttribute("message", "Username or email already exists.");
            return "register";
        }
    }
    
    
    
    
    
    
     
	/*
	 * @PostMapping("/register") public String userRegistration(@ModelAttribute
	 * Login login, Model model) {
	 * 
	 * boolean flag = loginService.userRegistration(login);
	 * model.addAttribute("message","User successfully Registered"); return
	 * "user-register"; }
	 */


}
