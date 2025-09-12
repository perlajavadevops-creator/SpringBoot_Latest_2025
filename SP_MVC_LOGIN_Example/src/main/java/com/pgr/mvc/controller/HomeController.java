package com.pgr.mvc.controller;

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

@Controller
@RequestMapping
public class HomeController {

	@GetMapping("/")
	public ModelAndView hello() {
		ModelAndView modelAndView = new ModelAndView("index");

		return modelAndView;
	}

	@GetMapping("/login")
	public String loginGet(@RequestParam String username, @RequestParam String password, Model model) {
		model.addAttribute("username", "Logined by : "+username);
		return "success";
	}

	@PostMapping("/login_post")
	public String loginPost(@ModelAttribute Login login, Model model) {
		model.addAttribute("username", "Logined by : "+login.getUsername());
		return "success";
	}
}