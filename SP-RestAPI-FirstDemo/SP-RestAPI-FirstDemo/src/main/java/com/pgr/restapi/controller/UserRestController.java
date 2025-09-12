package com.pgr.restapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pgr.restapi.dto.User;
import com.pgr.restapi.dto.UserStatus;
import com.pgr.restapi.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/users") // root uri// api/v1/users/8
public class UserRestController {

	/*
	 * @GetMapping(value="/first", produces = MediaType.APPLICATION_JSON_VALUE)
	 * public ResponseEntity<String> getHello() { User user = new User();
	 * user.setUsername("Reddy"); return new ResponseEntity(user, HttpStatus.OK); }
	 */

	@Autowired
	private UserRepository userRepository;

	// GET - Retrieve all users with pagination
	@GetMapping
	public ResponseEntity<Map<String, Object>> getAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDirection) {

		Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
		Page<User> userPage = userRepository.findAll(pageable);

		Map<String, Object> response = new HashMap<>();
		response.put("users", userPage.getContent());
		response.put("currentPage", userPage.getNumber());
		response.put("totalItems", userPage.getTotalElements());
		response.put("totalPages", userPage.getTotalPages());
		response.put("hasNext", userPage.hasNext());
		response.put("hasPrevious", userPage.hasPrevious());

		return ResponseEntity.ok(response);
	}

	// GET - Retrieve user by ID
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// GET - Search users by name
	@GetMapping("/search")
	public ResponseEntity<List<User>> searchUsersByName(@RequestParam String name) {
		List<User> users = userRepository.findByNameContaining(name);
		return ResponseEntity.ok(users);
	}

	// GET - Filter users by status
	@GetMapping("/status/{status}")
	public ResponseEntity<List<User>> getUsersByStatus(@PathVariable UserStatus status) {
		List<User> users = userRepository.findByStatus(status);
		return ResponseEntity.ok(users);
	}

	// POST - Create new user
	@PostMapping
	public ResponseEntity<Map<String, Object>> createUser(@Valid @RequestBody User user) {
		Map<String, Object> response = new HashMap<>();

		// Check if username or email already exists
		if (userRepository.existsByUsername(user.getUsername())) {
			response.put("error", "Username already exists");
			response.put("field", "username");
			return ResponseEntity.badRequest().body(response);
		}

		if (userRepository.existsByEmail(user.getEmail())) {
			response.put("error", "Email already exists");
			response.put("field", "email");
			return ResponseEntity.badRequest().body(response);
		}

		User savedUser = userRepository.save(user);
		response.put("message", "User created successfully");
		response.put("user", savedUser);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// PUT - Update entire user
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {

		Map<String, Object> response = new HashMap<>();

		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			response.put("error", "User not found");
			return ResponseEntity.notFound().build();
		}

		User existingUser = optionalUser.get();

		// Check for duplicate username/email (excluding current user)
		if (!existingUser.getUsername().equals(userDetails.getUsername())
				&& userRepository.existsByUsername(userDetails.getUsername())) {
			response.put("error", "Username already exists");
			return ResponseEntity.badRequest().body(response);
		}

		if (!existingUser.getEmail().equals(userDetails.getEmail())
				&& userRepository.existsByEmail(userDetails.getEmail())) {
			response.put("error", "Email already exists");
			return ResponseEntity.badRequest().body(response);
		}

		// Update user fields
		existingUser.setUsername(userDetails.getUsername());
		existingUser.setEmail(userDetails.getEmail());
		existingUser.setFirstName(userDetails.getFirstName());
		existingUser.setLastName(userDetails.getLastName());
		existingUser.setAge(userDetails.getAge());
		existingUser.setStatus(userDetails.getStatus());

		User updatedUser = userRepository.save(existingUser);
		response.put("message", "User updated successfully");
		response.put("user", updatedUser);

		return ResponseEntity.ok(response);
	}

	// PATCH - Partial update user
	@PatchMapping("/{id}")
	public ResponseEntity<Map<String, Object>> partialUpdateUser(@PathVariable Long id,
			@RequestBody Map<String, Object> updates) {

		Map<String, Object> response = new HashMap<>();

		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			response.put("error", "User not found");
			return ResponseEntity.notFound().build();
		}

		User existingUser = optionalUser.get();

		// Apply partial updates
		updates.forEach((key, value) -> {
			switch (key) {
			case "firstName" -> existingUser.setFirstName((String) value);
			case "lastName" -> existingUser.setLastName((String) value);
			case "age" -> existingUser.setAge((Integer) value);
			case "status" -> existingUser.setStatus(UserStatus.valueOf((String) value));
			}
		});

		User updatedUser = userRepository.save(existingUser);
		response.put("message", "User updated successfully");
		response.put("user", updatedUser);

		return ResponseEntity.ok(response);
	}

	// DELETE - Delete user by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
		Map<String, String> response = new HashMap<>();

		if (!userRepository.existsById(id)) {
			response.put("error", "User not found");
			return ResponseEntity.notFound().build();
		}

		userRepository.deleteById(id);
		response.put("message", "User deleted successfully");

		return ResponseEntity.ok(response);
	}

	// DELETE - Delete all users
	@DeleteMapping
	public ResponseEntity<Map<String, String>> deleteAllUsers() {
		userRepository.deleteAll();
		Map<String, String> response = new HashMap<>();
		response.put("message", "All users deleted successfully");
		return ResponseEntity.ok(response);
	}

}
