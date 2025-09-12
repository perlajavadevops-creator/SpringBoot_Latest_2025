package com.pgr.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/web")
public class HomeController {

	@GetMapping("/hello")
	public ModelAndView hello() {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("message", "Hello, Spring Boot MVC!");

		return modelAndView;
	}

	@GetMapping("/hello1")
	public String hello(Model model) {
		model.addAttribute("message", "Hello, Spring Boot MVC!");
		return "index";
	}

}