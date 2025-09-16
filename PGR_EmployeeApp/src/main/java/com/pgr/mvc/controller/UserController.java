package com.pgr.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pgr.mvc.dto.Login;
import com.pgr.mvc.entity.LoginEntity;
import com.pgr.mvc.service.LoginService;
import com.pgr.mvc.service.UserService;

@Controller
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	LoginService loginService;

	@GetMapping("/save")
	public ModelAndView showRegisterForm() {
		return new ModelAndView("createuser", "user", new Login());
	}

	// 🔄 Create or Update user

	@PostMapping(value = "/save")
	public ModelAndView saveUser(@ModelAttribute Login user, RedirectAttributes redirectAttributes) {
		userService.saveOrUpdateUser(user);
		redirectAttributes.addFlashAttribute("message", "User created successfully!");
		ModelAndView view = new ModelAndView("redirect:/profile");

		return view;
	}

	// 🔍 Get user by ID
	@GetMapping("/{id}")
	public Login getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}

	// 🔍 Get user by username (native query)
	@GetMapping("/native/{username}")
	public LoginEntity getUserByUsernameNative(@PathVariable String username) {
		return userService.getUserByUsernameNative(username);
	}

	// 🔍 Get user by username (JPQL)
	@GetMapping("/jpql/{username}")
	public LoginEntity getUserByUsernameJPQL(@PathVariable String username) {
		return userService.getUserByUsernameJPQL(username);
	}

	// 📋 Get all users
	@GetMapping("/all")
	public List<LoginEntity> getAllUsers() {
		return userService.getAllUsers();
	}

	// ✏️ Update password by username
	@PutMapping("/update-password")
	public String updatePassword(@RequestParam String username, @RequestParam String newPassword) {
		int updated = userService.updatePassword(username, newPassword);
		return updated > 0 ? "Password updated successfully" : "User not found or update failed";
	}

	// 🗑️ Delete user by ID
	@GetMapping("/delete/{id}")
	public ModelAndView deleteUserById(@PathVariable int id, RedirectAttributes redirectAttributes) {
		userService.deleteUserById(id);

		ModelAndView view = new ModelAndView("redirect:/profile");
		redirectAttributes.addFlashAttribute("message", "User deleted with ID: " + id);
		return view;
	}

	// Show edit form
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable int id, Model model) {
		Login user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "useredit"; // Thymeleaf template name
	}

	// Show edit form
	@GetMapping("/read/{id}")
	public String showReadUserForm(@PathVariable int id, Model model) {
		Login user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "userread"; // Thymeleaf template name
	}

	// Handle form submission
	@PostMapping("/update/{id}")
	public ModelAndView updateUser(@ModelAttribute("user") Login user, @PathVariable int id,
			RedirectAttributes redirectAttributes) {
		userService.updateUser(user, id);
		redirectAttributes.addFlashAttribute("message", "User updated successfully! " + id);
		ModelAndView view = new ModelAndView("redirect:/profile");

		return view;
	}

	@GetMapping("/dashboard")
	public ModelAndView login(RedirectAttributes redirectAttributes) {
		ModelAndView view = new ModelAndView("dashboard");

		return view;
	}

}
