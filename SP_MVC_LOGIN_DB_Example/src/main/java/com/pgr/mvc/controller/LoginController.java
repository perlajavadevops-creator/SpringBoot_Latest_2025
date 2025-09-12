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

import com.pgr.mvc.dto.Login;
import com.pgr.mvc.service.LoginService;

@Controller
@RequestMapping
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@GetMapping("/")
	public ModelAndView hello() {
		ModelAndView modelAndView = new ModelAndView("index");

		return modelAndView;
	}

	@PostMapping("/login")
	public String login(@ModelAttribute Login login, Model model) {
		
		String messsage = loginService.getLoginDetails(login);
		model.addAttribute("message",messsage);
		return "success";
	}
}